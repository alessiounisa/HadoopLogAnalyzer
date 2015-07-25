argv <- commandArgs(trailingOnly = TRUE)

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
#loadLibrary("rJava")
loadLibrary("labeling")
loadLibrary("colorspace")
loadLibrary("scales")
loadLibrary("ggplot2")
loadLibrary("plyr")
loadLibrary("reshape2")

speedUp <- function(TempoSequenziale, VettoreTempiHadoop, vettoreNodiUsati, output){
  su = TempoSequenziale / VettoreTempiHadoop
  efficienza = su / vettoreNodiUsati
  #plot(vettoreNodiUsati, su, type=c("l"))
  df = data.frame(VettoreTempiHadoop, vettoreNodiUsati, su, efficienza)
  
  p = ggplot(df, aes(df), environment = environment())
  p = p + geom_line(aes(x = vettoreNodiUsati, y =  su), colour = "red")  
  p = p + xlab("# nodes")
  p = p + ylab("Speedup")
  p = p + scale_x_continuous(expand = c(0, 0))
  p = p + scale_y_continuous(expand = c(0, 0))
  p = p + theme(panel.grid.major.y = element_line(colour = "black"), 
                panel.background = element_rect(fill = 'white', colour = 'black'), 
                panel.grid.major.x = element_blank(), 
                panel.grid.minor.x = element_blank(),
                axis.text = element_text(colour = "black"))
  
  p = p + ggtitle("Speedup Chart")
  
  outputSU = paste(output, "speedup_efficiency.pdf", sep = "")
  pdf(file = outputSU, width = 10, height = 10)
  print(p)
  
  p = ggplot(df, aes(df), environment = environment())
  p = p + geom_line(aes(x = vettoreNodiUsati, y =  efficienza), colour = "red")  
  p = p + xlab("# nodes")
  p = p + ylab("Speedup")
  p = p + scale_x_continuous(expand = c(0, 0))
  p = p + scale_y_continuous(expand = c(0, 0))
  p = p + theme(panel.grid.major.y = element_line(colour = "black"), 
                panel.background = element_rect(fill = 'white', colour = 'black'), 
                panel.grid.major.x = element_blank(), 
                panel.grid.minor.x = element_blank(),
                axis.text = element_text(colour = "black"))
  
  p = p + ggtitle("Efficiency Chart")
  
  print(p)
  dev.off()
}

result <- tryCatch({
  output = argv[1]
  dati = as.matrix(read.csv(file = './R_script/speedup.hla', header = FALSE, sep = ","))
  speedUp(TempoSequenziale = dati[3,1], VettoreTempiHadoop = dati[2,], vettoreNodiUsati = dati[1,], output)
}, error = function(err){
  print(paste("MY_ERROR: ", err))
})
