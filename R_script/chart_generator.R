# require(scales)
# require(ggplot2)
# require(plyr)
# library(reshape2)
# library(xlsx)

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
loadLibrary("openxlsx")
loadLibrary("methods")
loadLibrary("colorspace")
loadLibrary("scales")
loadLibrary("ggplot2")
loadLibrary("plyr")
loadLibrary("reshape2")
# install.packages("colorspace", lib=".", dependencies = TRUE)
# install.packages("ggplot2", lib=".", dependencies =  TRUE)
#library(ggplot2, lib.loc=".")

cbind.fill <- function(...) {                                                                                                                                                       
  transpoted <- lapply(list(...),t)                                                                                                                                                 
  transpoted_dataframe <- lapply(transpoted, as.data.frame)                                                                                                                         
  return (data.frame(t(rbind.fill(transpoted_dataframe))))                                                                                                                          
} 

mettiVirgolaCsv <-function(path){
  for(file in list.files(path = path, pattern = "*.csv$")){
    file = paste(path, file, sep="")
    prova = readLines(file)
    
    if(length(grep(pattern = "\"#send\"," , x = prova[7])) == 0){
      #print("diverso")
      prova[7] = paste(prova[7], ",", sep="")
      write(x = prova, file = file)
    }
  }
}

plotSingleNodeOther <- function(tipologiaTempo,dstat, value,.e, len, xlabel ,ylabel, colorLine, title, geom_color, printTitle = TRUE, colorType = color, ylimits = NULL, lineDash = FALSE,fontSize = NULL, fontFamily = NULL){ 
  print(title)
  p = ggplot(dstat, aes(dstat), environment = environment())
  
    for(val in value){
      p = p + geom_line(aes(x = len, y =  eval(parse(text=val)), colour = geom_color[1]))  
    }
    if(length(value) == 1){
      if(lineDash == TRUE) {
        p = p + geom_line(aes(x = len, y =  eval(parse(text=value[1])), colour = geom_color[1]), linetype = "dotted")   
      }
      else
        p = p + geom_line(aes(x = len, y =  eval(parse(text=value[1])), colour = geom_color[1]))
    }
    else{
      if(lineDash == TRUE){
        p = p + geom_line(aes(x = len, y =  eval(parse(text=value[1])), colour = geom_color[1]))   
        p = p + geom_line(aes(x = len, y =  eval(parse(text=value[2])), colour = geom_color[2]), linetype = "dotted")   
      }
      else{
        p = p + geom_line(aes(x = len, y =  eval(parse(text=value[1])), colour = geom_color[1]))   
        p = p + geom_line(aes(x = len, y =  eval(parse(text=value[2])), colour = geom_color[2]))   
      }
    }
    p = p + xlab(xlabel)
    p = p + ylab(ylabel)
    p = p + labs(color="Legend")
    p = p + scale_x_continuous(expand = c(0, 0))
    if(!is.null(ylimits)){
      p = p + scale_y_continuous(limits=ylimits ,expand = c(0, 0))
    }
    else{
      p = p + scale_y_continuous(expand = c(0, 0))
    }
    if(length(value) == 1){
      if(colorType == 0){
        p = p + scale_color_manual(values="black")  
      }
      else if(colorType == 1){
        p = p + scale_color_manual(values="red")
      }
    }
    else{
      if(colorType == 0){
      p = p + scale_color_manual(values=c("black", "grey"))
      }
      else if(colorType == 1){
        
      }
    }
    p = p + theme(legend.position = "bottom", 
                  panel.grid.major.y = element_line(colour = "black"), 
                  panel.background = element_rect(fill = 'white', colour = 'black'), 
                  panel.grid.major.x = element_blank(), 
                  panel.grid.minor.x = element_blank(),
                  axis.text = element_text(colour = "black" ,
                                           size = fontSize))
    
    if(printTitle){
      p = p + ggtitle(title)
    }
    print(p)
}

takeDelay <- function(fileInput){
  file = readLines(con = fileInput)
  a = sub(".*csv ", "", file[4])
  a = sub("\".*","", a)
  a = as.numeric(a)
  return <- a
}

average <- function(campo, ylabel, tipologiaTempo = "s", colorType, printTitle = TRUE, fontSize = fontSize, ylimits = NULL){
  
  .e <- environment()
  for(file in list.files(inputFile, pattern = "*.csv$")){
    title = file
    file = paste(inputFile, file, sep = "")
    if(length(grep(pattern = "master" , x = file)) == 1){
      dstat <- read.csv(file, skip=6)
      dstat <- subset(dstat, select = -X)
      dstat <- dstat[-1,]
      
      if(length(campo) != 1){
        campoEval = 0
        for(val in campo){
          campoEval = campoEval + eval(parse(text=val))
        }
        master = data.frame(campoEval)
      }
      else{
        master <- eval(parse(text=campo)) 
      }
    }
    else{
      dstat <- read.csv(file, skip=6)
      dstat <- subset(dstat, select = -X)
      dstat <- dstat[-1,]
      
      
      if(length(campo) != 1){
        campoEval = 0
        for(val in campo){
          campoEval = campoEval + eval(parse(text=val))
        } 
        if(exists("cpu")){
          cpu <- cbind.fill(cpu, campoEval)
        }
        else{
          cpu <- campoEval
        } 
      }
      else{
        campoEval = eval(parse(text=campo))  
        if(exists("cpu")){
          cpu <- cbind.fill(cpu, campoEval)
        }
        else{
          cpu <- campoEval
        }  
      }
    }  
  }
  cpu = cbind(cpu, NA)
  cpu[,dim(cpu)[2]] = rowMeans(x = cpu, na.rm = TRUE)
  
  cpu <- cbind.fill(cpu, master)

  delayDSTAT = takeDelay(file)
  if(tipologiaTempo == "s"){
    len <- seq(0,dim(cpu)[1]-1) * delayDSTAT
    xlabel = "Time (s)"
  }
  else if(tipologiaTempo == "m"){
    len <- (seq(0,dim(cpu)[1]-1) * delayDSTAT) / 60
    xlabel = "Time (m)"
  }
  else if(tipologiaTempo == "h"){
    len <- ((seq(0,dim(cpu)[1]-1) * delayDSTAT) / 60) / 60
    xlabel = "Time (h)"
  }
  plotSingleNodeOther(tipologiaTempo,cpu, c("dstat[, dim(dstat)[2]-1]", "dstat[, dim(dstat)[2]]"), .e , len, xlabel ,ylabel, colorLine, title = "Average", c("slave", "master"), printTitle, colorType, ylimits = ylimits,fontSize = fontSize)
}

AllNodes <- function(campo, ylabel, tipologiaTempo = "s", colorType, ylimits = NULL ,printTitle = TRUE, fontSize){
  .e <- environment()
  colnamesSlaves = NULL
  for(file in list.files(inputFile, pattern = "*.csv$")){
    title = file
    file = paste(inputFile, file, sep = "")
    delayDSTAT = takeDelay(file)
    dstat <- read.csv(file, skip=6)
    dstat <- subset(dstat, select = -X)
    dstat <- dstat[-1,]
    if(length(grep(pattern = "master" , x = file)) == 1){
      if(length(campo) != 1){
        campoEval = 0
        for(val in campo){
          campoEval = campoEval + eval(parse(text=val))
        }
        master = data.frame(campoEval)
      }
      else{
        master <- eval(parse(text=campo)) 
      }
    }
    else{
      colnamesSlaves = c(colnamesSlaves, title)
      if(length(campo) != 1){
        campoEval = 0
        for(val in campo){
          campoEval = campoEval + eval(parse(text=val))
        } 
        if(exists("cpu")){
          cpu <- cbind.fill(cpu, campoEval)
        }
        else{
          cpu <- campoEval
        } 
      }
      else{
        campoEval = eval(parse(text=campo))  
        if(exists("cpu")){
          cpu <- cbind.fill(cpu, campoEval)
        }
        else{
          cpu <- campoEval
        }  
      }
    }  
  }
  cpu <- cbind.fill(cpu, master)
  
  names(cpu) <- tolower(names(cpu))
  #cambiamo i nomi alle colonne con SlaveN e Master
  for(numeroColonna in 1:ncol(cpu)-1) {
    #variabile <- paste("Slave", gsub(pattern = "[^0-9]","", x = colnamesSlaves[numeroColonna]))
    variabile <- gsub(pattern = "[^0-9]","", x = colnamesSlaves[numeroColonna])
    colnames(cpu)[numeroColonna] <- variabile
  }
  colnames(cpu)[dim(cpu)[2]] <- "Master"
  cpu = cbind(cpu, seq(1, dim(cpu)[1]))
  colnames(cpu)[dim(cpu)[2]] <- paste("times")
  aql <- melt(cpu, id.vars = "times")
  nrow = round(dim(cpu)[2] / 20)
  if(nrow == 0){
    nrow = 1
  }
  if(tipologiaTempo == "s"){
    aql$times <- aql$times * delayDSTAT
    xlabel = "Time (s)"
  }
  else if(tipologiaTempo == "m"){
    aql$times <- (aql$times * delayDSTAT) / 60
    xlabel = "Time (m)"
  }
  else if(tipologiaTempo == "h"){
    aql$times <- ((aql$times * delayDSTAT) / 60) / 60
    xlabel = "Time (h)"
  }
  
  p = ggplot(aql, aes(times,value, col=variable))
  p = p + geom_line()
  p = p + xlab(xlabel)
  p = p + ylab(ylabel)
  p = p + labs(color="Legend")
  p = p + scale_x_continuous(expand = c(0, 0))
  p = p + guides(col = guide_legend(nrow = nrow))
  p = p + theme(legend.position = "bottom", 
                panel.grid.major.y = element_line(colour = "black"), 
                panel.background = element_rect(fill = 'white', colour = 'black'), 
                panel.grid.major.x = element_blank(), 
                panel.grid.minor.x = element_blank(),
                axis.text = element_text(colour = "black" ,
                                         size = fontSize))
  if(!is.null(ylimits)){
    p = p + scale_y_continuous(limits=ylimits ,expand = c(0, 0))
  }
  else{
    p = p + scale_y_continuous(expand = c(0, 0))
  }
  title = "All Nodes"
  if(printTitle){
    p = p + ggtitle(title)
  }
  print(p)
}



singleNode <- function(tipologiaTempo, value, value2 = NULL,ylabel, geom_color ,printTitle, colorType, lineDash, ylimits, fontSize){
  for(file in list.files(inputFile, pattern = "*.csv$")){
    title = file
    file = paste(inputFile, file, sep = "")
    delayDSTAT = takeDelay(file)
    dstat <- read.csv(file, skip=6)
    dstat <- subset(dstat, select = -X)
    dstat <- dstat[-1,]
    .e <- environment()
    
    if(tipologiaTempo == "s"){
      len <- (seq(0,length(dstat$used)-1) * delayDSTAT)
      xlabel = "Time (s)"
    }
    else if(tipologiaTempo == "m"){
      len <- (seq(0,length(dstat$used)-1) * delayDSTAT) / 60
      xlabel = "Time (m)"
    }
    else if(tipologiaTempo == "h"){
      len <- ((seq(0,length(dstat$used)-1) * delayDSTAT) / 60) / 60
      xlabel = "Time (h)"
    }
    if(is.null(value2)){
      if(length(value) != 1){
        campoEval = 0
        for(val in value){
          campoEval = campoEval + eval(parse(text=val))
        }
        campoEval = data.frame(campoEval)
        #YLIMITS MANCANTE
        plotSingleNodeOther(tipologiaTempo = tipologiaTempo, dstat = campoEval, value =  "dstat$campoEval", .e = .e, len = len,xlabel =  xlabel ,ylabel = ylabel,colorLine =  colorLine,title = title ,geom_color = geom_color, printTitle = printTitle, colorType = colorType, ylimits = ylimits, lineDash = lineDash ,fontSize = fontSize)
      }
      else{
        #YLIMITS MANCANTE
        plotSingleNodeOther(tipologiaTempo,dstat, value,.e, len, xlabel ,ylabel, colorLine, title, geom_color, printTitle, colorType, ylimits = ylimits, lineDash = lineDash, fontSize = fontSize)
      }
    }
    else{
      #YLIMITS MANCANTE
      plotSingleNodeOther(tipologiaTempo = tipologiaTempo, dstat,value =  c(value,value2), .e = .e, len = len,xlabel =  xlabel ,ylabel = ylabel,colorLine =  colorLine,title = title ,geom_color = geom_color, printTitle = printTitle, colorType = colorType, ylimits = ylimits,lineDash = lineDash ,fontSize = fontSize)
    }
}
}


result <- tryCatch({
input = readLines('./R_script/input.hla')
tempo = input[1] #sostituito
color = as.numeric(input[2]) #sostituito
dash = as.logical(input[3]) #sostituito
titoli = as.logical(input[4]) #sostituito
oneFilePdf = as.logical(input[5]) 
graphType = as.numeric(input[6]) #fatta divisione tra SINGOLI, MEDIE, TUTTI
CPUType = input[7] #splittato in stringhe
RAMType = input[8] #splittato in stringhe
Upperbound_RAM = input[9] #fatto
IOType = as.numeric(input[10]) #la divisione e' solo nei singoli, FATTA
PagingType = as.numeric(input[11]) #la divisione e' solo nei singoli, FATTA
NetType = as.numeric(input[12]) #la divisione e' solo nei singoli, FATTA
NPacketType = as.numeric(input[13]) #la divisione e' solo nei singoli, FATTA
font = as.numeric(input[14]) #sostituito
inputFile <<- input[15]
output = input[16]

if(CPUType[1] == "NULL"){
  CPUType = NULL
}else {
  CPUType = strsplit(CPUType, split = " ")
  CPUType = CPUType[[1]]
}
if(RAMType[1] == "NULL"){
  RAMType = NULL
}else{
  RAMType = strsplit(RAMType, split = " ")
  RAMType = RAMType[[1]]
}
if(Upperbound_RAM == "NULL"){
  Upperbound_RAM = NULL
} else{
  Upperbound_RAM = as.numeric(Upperbound_RAM)
  Upperbound_RAM = c(0, Upperbound_RAM)
}
ylimits = Upperbound_RAM
#tipologiaTempo  valori = "s" "m" "h"
#combCPU "" o c("dstat$usr", dstat$sys) combinazione dei campi cpu
#combRAM "" o c("dstat$used", dstat$free) combinazione dei campi RAM
#tipologiaColori 0 = grigi, 1 = colorati, 2 = linee diverse
#printTitle  TRUE, FALSE
mettiVirgolaCsv(inputFile)
if(graphType == 1){
  singleOutput = paste(output, "SinglePlot.pdf", sep = "")
  multiOutput = paste(output, "SinglePlot%04d.pdf", sep = "")
  pdf(file = ifelse(oneFilePdf, singleOutput, multiOutput), width = 10, onefile = oneFilePdf)

  #CPU
  if(is.null(CPUType)){
  singleNode(tipologiaTempo = tempo, value = "dstat$usr", ylabel = "CPUs Usr(%)", geom_color= "cpu", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$sys", ylabel = "CPUs Sys(%)", geom_color= "cpu", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$idl", ylabel = "CPUs Idl(%)", geom_color= "cpu", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$wai", ylabel = "CPUs Wai(%)", geom_color= "cpu", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$hiq", ylabel = "CPUs Hiq(%)", geom_color= "cpu", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$siq", ylabel = "CPUs Siq(%)", geom_color= "cpu", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  }
  else{
  singleNode(tipologiaTempo = tempo, value = CPUType, ylabel = "CPUs (%)", geom_color= "cpu", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  }
  #MEMORIA
  if(is.null(RAMType)){
  singleNode(tipologiaTempo = tempo, value = "dstat$used/1048576", ylabel = "Memory Used (MB)", geom_color= "memory", printTitle = titoli, colorType = color, ylimits = ylimits, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$buff/1048576", ylabel = "Memory Buff (MB)", geom_color= "memory", printTitle = titoli, colorType = color, ylimits = ylimits, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$cach/1048576", ylabel = "Memory Cach (MB)", geom_color= "memory", printTitle = titoli, colorType = color, ylimits = ylimits, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$free/1048576", ylabel = "Memory Free (MB)", geom_color= "memory", printTitle = titoli, colorType = color, ylimits = ylimits, lineDash = dash, fontSize = font)
  } else{
  singleNode(tipologiaTempo = tempo, value = RAMType, ylabel = "Memory (MB)", geom_color= "memory", printTitle = titoli, colorType = color, ylimits = ylimits, lineDash = dash, fontSize = font)
  }
  #PAGING
  if(PagingType == 1){
    singleNode(tipologiaTempo = tempo, value = "dstat$in.", ylabel = "Paging in", geom_color= "in", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
    singleNode(tipologiaTempo = tempo, value = "dstat$out", ylabel = "Paging out", geom_color= "out", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  } else if(PagingType == 2){
  singleNode(tipologiaTempo = tempo, value = "dstat$in.", value2 = "dstat$out", ylabel = "Paging", geom_color= c("in", "out"), printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  }
  #SWAP
  singleNode(tipologiaTempo = tempo, value = "dstat$used.1/1048576", ylabel = "Swap (MB)", geom_color= "swap", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  
  #DISCO
  if(IOType == 1){
    singleNode(tipologiaTempo = tempo, value = "dstat$read/1048576", ylabel = "Disk Usage Read (MB/s)", geom_color= "read", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
    singleNode(tipologiaTempo = tempo, value = "dstat$writ/1048576", ylabel = "Disk Usage Write (MB/s)", geom_color= "write", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  } else if(IOType == 2){
    singleNode(tipologiaTempo = tempo, value = "dstat$writ/1048576", value2 = "dstat$read/1048576",  ylabel = "Disk Usage (MB/s)", geom_color= c("read", "write"), printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  }
  #RETE - MB
  if(NetType == 1){
    singleNode(tipologiaTempo = tempo, value = "dstat$recv/1048576", ylabel = "Net Usage Receive (MB/s)", geom_color= "received", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
    singleNode(tipologiaTempo = tempo, value = "dstat$send/1048576", ylabel = "Net Usage Send (MB/s)", geom_color= "sent", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  } else if(NetType == 2){
    singleNode(tipologiaTempo = tempo, value = "dstat$recv", value2 = "dstat$send",ylabel = "Network Usage (MB)", geom_color= c("recv", "sent"), printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  }
  #RETE - PACCHETTI
  if(NPacketType == 1){
  singleNode(tipologiaTempo = tempo, value = "dstat$X.recv", ylabel = "Net Packets Receive (#)", geom_color= "Pks. received", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  singleNode(tipologiaTempo = tempo, value = "dstat$X.send", ylabel = "Net Packets Send (#)", geom_color= "Pks. sent", printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  } else if(NPacketType == 2){
  singleNode(tipologiaTempo = tempo, value = "dstat$X.send", value2 = "dstat$X.recv", ylabel = "Net Packets (#)", geom_color= c("Pks. received", "Pks. sent"), printTitle = titoli, colorType = color, ylimits = NULL, lineDash = dash, fontSize = font)
  }
  
  #INTERRUPT
  #singleNode(tipologiaTempo = tempo, value = "dstat$int", ylabel = "Interrupts (#)", geom_color= "Interrupts", printTitle = titoli, colorType = color, lineDash = dash, fontSize = font)
  
  #CONTEXT SWITCHES
  #singleNode(tipologiaTempo = tempo, value = "dstat$csw", ylabel = "Context Switches (#)", geom_color= "Context Switches", printTitle = titoli, colorType = color, lineDash = dash, fontSize = font)
  dev.off()
} else if(graphType == 2){
  
  singleOutput = paste(output, "AveragePlot.pdf", sep = "")
  multiOutput = paste(output, "AveragePlot%04d.pdf", sep = "")
  pdf(file = ifelse(oneFilePdf, singleOutput, multiOutput), width = 10, onefile = oneFilePdf)
  
  
  
  if(is.null(CPUType)){
    average("dstat$usr", ylabel = "CPUs Usr (%)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
    average("dstat$sys", ylabel = "CPUs Sys (%)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
    average("dstat$idl", ylabel = "CPUs Idl (%)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
    average("dstat$wai", ylabel = "CPUs Wai (%)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
    average("dstat$hiq", ylabel = "CPUs Hiq (%)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
    average("dstat$siq", ylabel = "CPUs Siq (%)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  } else{
    average(campo = CPUType, ylabel = "CPUs (%)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font)
  }
  if(is.null(RAMType)){
    average("dstat$used/1048576", ylabel = "Memory Used (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
    average("dstat$buff/1048576", ylabel = "Memory Buffer (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
    average("dstat$cach/1048576", ylabel = "Memory Cache (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
    average("dstat$free/1048576", ylabel = "Memory Free (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
 } else{
   average(campo = RAMType, ylabel = "Memory (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
 }
  average("dstat$in./1048576", ylabel = "Paging In", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  average("dstat$out/1048576", ylabel = "Paging Out", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  
  average("dstat$used.1/1048576", ylabel = "Swap (MB)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  
  average("dstat$read/1048576", ylabel = "Disk usage Read (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  average("dstat$writ/1048576", ylabel = "Disk usage Write (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  
  average("dstat$recv/1048576", ylabel = "Net Usage Received (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  average("dstat$send/1048576", ylabel = "Net Usage Sent (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  
  average("dstat$X.recv", ylabel = "Packets Received (#)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  average("dstat$X.send", ylabel = "Packets Sent (#)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = NULL)
  dev.off()
  average("dstat$int", ylabel = "Interrupts (#)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font)
  average("dstat$csw", ylabel = "Context Switches (#)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font)
} else if(graphType == 3){
  
  singleOutput = paste(output, "AllNodesPlot.pdf", sep = "")
  multiOutput = paste(output, "AllNodesPlot%04d.pdf", sep = "")
  pdf(file = ifelse(oneFilePdf, singleOutput, multiOutput), width = 10, onefile = oneFilePdf)
  
  if(is.null(CPUType)){
    AllNodes("dstat$usr", ylabel = "CPUs Usr (%)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
    AllNodes("dstat$sys", ylabel = "CPUs Sys (%)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
    AllNodes("dstat$idl", ylabel = "CPUs Idl (%)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
    AllNodes("dstat$wai", ylabel = "CPUs Wai (%)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
    AllNodes("dstat$hiq", ylabel = "CPUs Hiq (%)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
    AllNodes("dstat$siq", ylabel = "CPUs Siq (%)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  }else{
    AllNodes(CPUType, ylabel = "CPUs (%)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  }
  if(is.null(RAMType)){
  AllNodes("dstat$used/1048576", ylabel = "Memory Used (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
  AllNodes("dstat$buff/1048576", ylabel = "Memory Buffer (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
  AllNodes("dstat$cach/1048576", ylabel = "Memory Cache (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
  AllNodes("dstat$free/1048576", ylabel = "Memory Free (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
  } else{
  AllNodes(RAMType, ylabel = "Memory (MB/s)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font, ylimits = ylimits)
  }
  AllNodes("dstat$in./1048576", ylabel = "Paging In", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  AllNodes("dstat$out/1048576", ylabel = "Paging Out", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  
  AllNodes("dstat$used.1/1048576", ylabel = "Swap (MB)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  
  AllNodes("dstat$read/1048576", ylabel = "Disk usage Read (MB/s)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  AllNodes("dstat$writ/1048576", ylabel = "Disk usage Write (MB/s)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  
  AllNodes("dstat$recv/1048576", ylabel = "Net Usage Received (MB/s)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  AllNodes("dstat$send/1048576", ylabel = "Net Usage Sent (MB/s)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  
  AllNodes("dstat$X.recv", ylabel = "Packets Received (#)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  AllNodes("dstat$X.send", ylabel = "Packets Sent (#)", tipologiaTempo = tempo, colorType = color, ylimits = NULL, printTitle = titoli, fontSize = font)
  
  AllNodes("dstat$int", ylabel = "Interrupts (#)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font)
  AllNodes("dstat$csw", ylabel = "Context Switches (#)", tipologiaTempo = tempo, colorType = color, printTitle = titoli, fontSize = font)
  dev.off()
}
}, error = function(err){
	print(paste("MY_ERROR: ", err))
})