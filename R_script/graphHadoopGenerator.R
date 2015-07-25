#require(ggplot2)
#library(XML)
#library(intervals)

argv <- commandArgs(trailingOnly = TRUE)
print("Inizialiting Environment...")
loadLibrary <- function(lib){
  if(require(package = lib, character.only = TRUE, lib.loc=".")){
    print(paste(lib,"is loaded correctly", sep= " "))
  } else {
    print(paste("trying to install ", lib, sep = " "))
    install.packages(lib, lib=".", dependencies = TRUE)
    if(require(package = lib, character.only = TRUE, lib.loc=".")){
      print(paste(lib,"installed and loaded", sep = " "))
    } else {
      stop(paste("could not install", lib, sep = " "))
    }
  }
}

r <- getOption("repos")             # hard code the US repo for CRAN
r["CRAN"] <- "http://cran.us.r-project.org"
options(repos = r)
#Sys.setenv(JAVA_HOME='C:\\Program Files\\Java\\jre1.8.0_25')
loadLibrary("labeling")
loadLibrary("ggplot2")
loadLibrary("XML")
loadLibrary("intervals")


is.integer0 <- function(x)
{
  is.integer(x) && length(x) == 0L
}



printf <- function(...)print(sprintf(...))
tablehead = c("startTime", "finishTime", "elapsedTime", "progress", "id", "rack", "state", "status", "nodeHttpAddress", "diagnostics", "type", "assignedContainerId")
for(file in list.files(argv[1])) {
    file = paste(argv[1], file, sep = "")
    content = readLines(file)
    pagetree <- xmlInternalTreeParse(content, error=function(...){}, useInternalNodes = TRUE)
    tablehead = c("StartTime", "FinishTime", "ElapsedTime", "Progress", "Attempt", "Rack", "State", "Status", "Node", "Note", "Type", "AssignedContainerId")
    tablebody = xpathSApply(pagetree, "//*/taskAttempt/*", xmlValue)
    if(length(tablebody) == 0){
      next;
    }
    tabella = matrix(tablebody, ncol = length(tablehead), byrow = TRUE)
    newframe = data.frame(tabella)
    colnames(newframe) <- tablehead
    newframe <- subset(newframe, select = c(Attempt,State,Status,Node,StartTime,FinishTime,ElapsedTime,Note))
    
    if(exists("miaVariabile")){
      miaVariabile <- rbind(miaVariabile, newframe)
    }else{
      miaVariabile <- newframe
    }
}

miaVariabile$StartTime = levels(miaVariabile$StartTime)[miaVariabile$StartTime]
#miaVariabile$StartTime = strptime(miaVariabile$StartTime, "%a, %d %b %Y %H:%M:%S")

miaVariabile$FinishTime = levels(miaVariabile$FinishTime)[miaVariabile$FinishTime]
#miaVariabile$FinishTime = strptime(miaVariabile$FinishTime, "%a, %d %b %Y %H:%M:%S")

miaVariabile$StartTime= as.numeric(miaVariabile$StartTime)
miaVariabile$FinishTime = as.numeric(miaVariabile$FinishTime)

miaVariabile$FinishTime = miaVariabile$FinishTime - miaVariabile$StartTime
miaVariabile$StartTime = miaVariabile$StartTime - min(miaVariabile$StartTime)
miaVariabile$FinishTime = miaVariabile$FinishTime + miaVariabile$StartTime



#importare la libreria Intervals
nuovoFrame = subset(x = miaVariabile, select = c(Node, StartTime, FinishTime))
nuovoFrame = nuovoFrame[with(nuovoFrame, order(Node)), ]

invisible(by(data = nuovoFrame, INDICES = nuovoFrame$Node, FUN = function(x){
  mat = matrix(data = c(x$StartTime, x$FinishTime), ncol = 2)
  if(dim(mat)[1] > 1){
    ord = order(mat[,1])
    mat = mat[ord,]
  }
  A = Intervals(mat)
  ol <<-interval_overlap(A,A)

  mat1 = mat
  mat1 = cbind(mat1, NA)

  num = 0
  for(i in 1:length(mat1[,3])){
    if(is.na(mat1[i,3])){
      num = num + 1
      mat1[i,3] = num
      interval = A[i]
      for(j in (i):length(mat1[,3])){
        if(is.na(mat1[j,3])){
          test = interval_overlap(interval,A[j])
          if(is.integer0(test[[1]])){
            mat1[j,3] = num
            interval = Intervals(c(mat1[i,1], mat1[j,2]))
          }
        }
      }
    }
  }
  if(!exists("matComp")){
    matComp <<- mat1
  } else{
  matComp <<- rbind(matComp,mat1)
  }
}))

frameP = data.frame(matComp)
frameP = cbind(frameP, nuovoFrame$Node)
frameP = cbind(frameP, miaVariabile$State)
colnames(frameP) = c("StartTime", "FinishTime", "LineNumber", "Node", "State")
frameP$Node = sub(".*/","",frameP$Node)
frameP$Node = sub(":.*","", frameP$Node)
frameP$Node = paste(frameP$Node, "/")
frameP$Node = paste(frameP$Node, frameP$LineNumber)
vec = frameP$Node == "100.0 / 1"
frameP = frameP[!vec,] 
bol1  = frameP$State == "SUCCEEDED"
bol2  = frameP$State == "KILLED"
bol3  = frameP$State == "FAILED"
vec2 = bol1 | bol2 | bol3
frameP = frameP[vec2,]
frameP$StartTime = frameP$StartTime/1000
frameP$FinishTime = frameP$FinishTime/1000
# ggplot(frameP, aes(colour=State)) + 
#   geom_segment(aes(x=StartTime, xend=FinishTime, y=Node, yend=Node), size=6, position = "identity", lineend = "round") +
#   xlab("Duration")

frameP$FinishTime = frameP$FinishTime -5

plotLogHadoop <- function(tipologiaTempo,dstat, .e, len, title, printTitle = TRUE, fontSize = NULL, showYLabel = FALSE){
  if(tipologiaTempo == "m"){
    divisionTime = 60
    xlab = "Time (m)"
  }
  else if(tipologiaTempo == "h"){
    divisionTime = 60*60
    xlab = "Time (h)"
  }
  else{
    divisionTime = 1
    xlab = "Time (s)"
  }

  if(levels(dstat$State)[1] == "SUCCEEDED" && levels(dstat$State)[2] == "FAILED" && levels(dstat$State)[3] == "KILLED"){
    myColors <- c("green", "red","yellow")
    names(myColors) = levels(dstat$State)[1:3]
  }
  else if(levels(dstat$State)[1] == "SUCCEEDED" && levels(dstat$State)[2] == "KILLED"){
    myColors <- c("green", "yellow")
    names(myColors) = levels(dstat$State)[1:2]
  }
  else if(levels(dstat$State)[1] == "SUCCEEDED" && levels(dstat$State)[2] == "FAILED"){
    myColors <- c("green", "red")
    names(myColors) = levels(dstat$State)[1:2]
  }
  else if(levels(dstat$State)[1] == "FAILED" && levels(dstat$State)[2] == "KILLED"){
    myColors <- c("red", "yellow")
    names(myColors) = levels(dstat$State)[1:2]
  }
   else{
    myColors <- c("green")
    names(myColors) = levels(dstat$State)[1]
  }
  
  p = ggplot(frameP, aes(colour=State), environment = environment())
  p = p + geom_segment(aes(x=StartTime/divisionTime, xend=FinishTime/divisionTime, y=Node, yend=Node), size=1, lineend = "round")
  p = p +  xlab(xlab)
  p = p + scale_x_continuous(expand = c(0, 0))
  p = p + scale_colour_manual(name = "State",values = myColors)
  #p = p + scale_y_discrete(limits = rev(frameP$Node), expand = c(0.1,0))
  if(showYLabel == FALSE){
    p = p + theme(legend.position = "right", 
                panel.grid.major.y = element_line(colour = "white"), 
                panel.background = element_rect(fill = 'white', colour = 'black'), 
                panel.grid.major.x = element_blank(), 
                panel.grid.minor.x = element_blank(),
                axis.text = element_text(colour = "black" ,
                                         size = fontSize), axis.text.y = element_blank()) 
  }
  else{
    p = p + theme(legend.position = "right", 
                  panel.grid.major.y = element_blank(), 
                  panel.background = element_rect(fill = 'white', colour = 'black'), 
                  panel.grid.major.x = element_blank(), 
                  panel.grid.minor.x = element_blank(),
                  axis.text = element_text(colour = "black" ,
                                           size = fontSize))
  }
  if(printTitle){
    p = p + ggtitle(title)
  }
  print(p)
}

output = argv[2]
output = paste(output, "hadoopGraph.pdf", sep = "")
tipologiaTempo = argv[3]
showYLabel = as.logical(argv[4])

pdf(file = output, width = 15, height = 15)
plotLogHadoop(tipologiaTempo,dstat =  frameP, .e = environment(), title = "Log Hadoop", printTitle = TRUE, fontSize = 8, showYLabel = showYLabel)
invisible(dev.off())

print("Hadoop Graph Generated...Done!")