input = readLines('./R_script/inputExcel.hla')
inputFile <<- input[1]
output <<- input[2]

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
loadLibrary("labeling")
loadLibrary("openxlsx")
loadLibrary("methods")

takeDelay <- function(fileInput){
  file = readLines(con = fileInput)
  a = sub(".*csv ", "", file[4])
  a = sub("\".*","", a)
  a = as.numeric(a)
  return <- a
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

mettiVirgolaCsv(inputFile)

createXLS <- function(){
  .e <- environment()
  fileOutput = output
  
  if(file.exists(fileOutput)) {
    file.remove(fileOutput)
  }
  for(file in list.files(inputFile, pattern = "*.csv$")){
    file = paste(inputFile, file, sep = "")
    delayDSTAT = takeDelay(file)
    dstat <- read.csv(file, skip=6)
    dstat <- subset(dstat, select = -X)
    dstat <- dstat[-1,]
    if(length(grep(pattern = "master" , x = file)) == 1){
      cpuMasterUsr <- dstat$usr
      cpuMasterSys <- dstat$sys
      cpuMasterIdl <- dstat$idl
      cpuMasterWai <- dstat$wai
      cpuMasterHiq <- dstat$hiq
      cpuMasterSiq <- dstat$siq
      cpuMasterSomma <- dstat$usr + dstat$sys
      
      memoryMasterUsed <- dstat$used/1048576
      memoryMasterBuff <- dstat$buff/1048576
      memoryMasterCach <- dstat$cach/1048576
      memoryMasterFree <- dstat$free/1048576
      memoryMasterSomma <- dstat$used/1048576 + dstat$buff/1048576 + dstat$cach/1048576

      swapMaster <- dstat$used.1/1048576
      discoReadMaster <- dstat$read/1048576
      discoWriteMaster <- dstat$writ/1048576
      pacchettiRicevutiMaster <- dstat$X.recv
      pacchettiInviatiMaster <- dstat$X.send
      throughputReteRicevutiMaster <- dstat$recv/1048576
      throughputReteInviatiMaster <- dstat$send/1048576

      dstatMediaCPUMasterUsr <- mean(cpuMasterUsr)
      dstatMediaCPUMasterSys <- mean(cpuMasterSys)
      dstatMediaCPUMasterIdl <- mean(cpuMasterIdl)
      dstatMediaCPUMasterWai <- mean(cpuMasterWai)
      dstatMediaCPUMasterHiq <- mean(cpuMasterHiq)
      dstatMediaCPUMasterSiq <- mean(cpuMasterSiq)
      dstatMediaCPUMasterSomma <- mean(cpuMasterSomma)
      
      dstatMediaMemoriaMasterUsed <- mean(memoryMasterUsed)
      dstatMediaMemoriaMasterBuff <- mean(memoryMasterBuff)
      dstatMediaMemoriaMasterCach <- mean(memoryMasterCach)
      dstatMediaMemoriaMasterFree <- mean(memoryMasterFree)
      dstatMediaMemoriaMasterSomma <- mean(memoryMasterSomma)
      
      dstatMediaSwapMaster <- mean(swapMaster)
      dstatMediaDiscoReadMaster <- mean(discoReadMaster)
      dstatMediaDiscoWriteMaster <- mean(discoWriteMaster)
      dstatMediaPacchettiRicevutiMaster <- mean(pacchettiRicevutiMaster)
      dstatMediaPacchettiInviatiMaster <- mean(pacchettiInviatiMaster)
      dstatMediaThroughputReteRicevutiMaster <- mean(throughputReteRicevutiMaster)
      dstatMediaThroughputReteInviatiMaster <- mean(throughputReteInviatiMaster)
    }
    else{
      #CPU
      if(exists("cpuUsr")){ 
        cpuUsr <- cbind(cpuUsr, dstat$usr)
      }
      else {
        cpuUsr <- dstat$usr
      }
      if(exists("cpuSys")){ 
        cpuSys <- cbind(cpuSys, dstat$sys)
      }
      else {
        cpuSys <- dstat$sys
      }
      if(exists("cpuIdl")){ 
        cpuIdl <- cbind(cpuIdl, dstat$idl)
      }
      else {
        cpuIdl <- dstat$idl
      }
      if(exists("cpuWai")){ 
        cpuWai <- cbind(cpuWai, dstat$wai)
      }
      else {
        cpuWai <- dstat$wai
      }
      if(exists("cpuHiq")){ 
        cpuHiq <- cbind(cpuHiq, dstat$hiq)
      }
      else {
        cpuHiq <- dstat$hiq
      }
      if(exists("cpuSiq")){ 
        cpuSiq <- cbind(cpuSiq, dstat$siq)
      }
      else {
        cpuSiq <- dstat$siq
      }
      if(exists("cpuSomma"))
        cpuSomma <- cbind(cpuSomma, dstat$usr + dstat$sys)
      else {
        cpuSomma <- dstat$usr + dstat$sys
      }
      
      if(exists("dstatMediaCPUUsr"))
        dstatMediaCPUUsr <- cbind(dstatMediaCPUUsr, mean(cpuUsr))
      else {
        dstatMediaCPUUsr <- cbind(mean(cpuUsr))
      }
      if(exists("dstatMediaCPUSys"))
        dstatMediaCPUSys <- cbind(dstatMediaCPUSys, mean(cpuSys))
      else {
        dstatMediaCPUSys <- cbind(mean(cpuSys))
      }
      if(exists("dstatMediaCPUIdl"))
        dstatMediaCPUIdl <- cbind(dstatMediaCPUIdl, mean(cpuIdl))
      else {
        dstatMediaCPUIdl <- cbind(mean(cpuIdl))
      }
      if(exists("dstatMediaCPUWai"))
        dstatMediaCPUWai <- cbind(dstatMediaCPUWai, mean(cpuWai))
      else {
        dstatMediaCPUWai <- cbind(mean(cpuWai))
      }
      if(exists("dstatMediaCPUHiq"))
        dstatMediaCPUHiq <- cbind(dstatMediaCPUHiq, mean(cpuHiq))
      else {
        dstatMediaCPUHiq <- cbind(mean(cpuHiq))
      }
      if(exists("dstatMediaCPUSiq"))
        dstatMediaCPUSiq <- cbind(dstatMediaCPUSiq, mean(cpuSiq))
      else {
        dstatMediaCPUSiq <- cbind(mean(cpuSiq))
      }
      if(exists("dstatMediaCPUSomma")) {
        dstatMediaCPUSomma <- cbind(dstatMediaCPUSomma, mean(cpuSomma))
      }
      else {
        dstatMediaCPUSomma <- cbind(mean(cpuSomma))
      }

      #MEMORIA
      if(exists("memoryUsed")) {
        memoryUsed <- cbind(memoryUsed, dstat$used/1048576)
      }
      else {
        memoryUsed <- dstat$used/1048576
      } 
      if(exists("memoryBuff")) {
        memoryBuff <- cbind(memoryBuff, dstat$buff/1048576)
      }
      else {
        memoryBuff <- dstat$buff/1048576
      }
      if(exists("memoryCach")) {
        memoryCach <- cbind(memoryCach, dstat$cach/1048576)
      }
      else {
        memoryCach <- dstat$cach/1048576
      }
      if(exists("memoryFree")) {
        memoryFree <- cbind(memoryFree, dstat$free/1048576)
      }
      else {
        memoryFree <- dstat$free/1048576
      }
      if(exists("memorySomma")) {
        memorySomma <- cbind(memorySomma, dstat$used/1048576 + dstat$buff/1048576 + dstat$cach/1048576)        
      }
      else {
        memorySomma <- dstat$used/1048576 + dstat$buff/1048576 + dstat$cach/1048576
      }


      if(exists("dstatMediaMemoriaUsed"))
        dstatMediaMemoriaUsed <- cbind(dstatMediaMemoriaUsed, mean(memoryUsed))
      else {
        dstatMediaMemoriaUsed <- cbind(mean(memoryUsed))
      }
      if(exists("dstatMediaMemoriaBuff"))
        dstatMediaMemoriaBuff <- cbind(dstatMediaMemoriaBuff, mean(memoryBuff))
      else {
        dstatMediaMemoriaBuff <- cbind(mean(memoryBuff))
      }
      if(exists("dstatMediaMemoriaCach"))
        dstatMediaMemoriaCach <- cbind(dstatMediaMemoriaCach, mean(memoryCach))
      else {
        dstatMediaMemoriaCach <- cbind(mean(memoryCach))
      }
      if(exists("dstatMediaMemoriaFree"))
        dstatMediaMemoriaFree <- cbind(dstatMediaMemoriaFree, mean(memoryFree))
      else {
        dstatMediaMemoriaFree <- cbind(mean(memoryFree))
      }
      if(exists("dstatMediaMemoriaSomma")) {
        dstatMediaMemoriaSomma <- cbind(dstatMediaMemoriaSomma, mean(memorySomma))
      }
      else {
        dstatMediaMemoriaSomma <- cbind(mean(memorySomma))
      }

      #SWAP
      if(exists("swap")) {
        swap <- cbind(swap, dstat$used.1/1048576)
      }
      else {
        swap <- dstat$used.1/1048576
      }
      if(exists("dstatMediaSwap"))
        dstatMediaSwap <- cbind(dstatMediaSwap, mean(swap))
      else {
        dstatMediaSwap <- cbind(mean(swap))
      }
      #DISCO READ
      if(exists("discoRead")) {
        discoRead <- cbind(discoRead, dstat$read/1048576)
      }
      else {
        discoRead <- dstat$read/1048576
      }
      if(exists("dstatMediaDiscoRead"))
        dstatMediaDiscoRead <- cbind(dstatMediaDiscoRead, mean(discoRead))
      else {
        dstatMediaDiscoRead <- cbind(mean(discoRead))
      }
      #DISCO WRITE
      if(exists("discoWrite")) {
        discoWrite <- cbind(discoWrite, dstat$writ/1048576)
      }
      else {
        discoWrite <- dstat$writ/1048576
      }
      if(exists("dstatMediaDiscoWrite"))
        dstatMediaDiscoWrite <- cbind(dstatMediaDiscoWrite, mean(discoWrite))
      else {
        dstatMediaDiscoWrite <- cbind(mean(discoWrite))
      }
      #PACCHETTI RICEVUTI
      if(exists("pacchettiRicevuti")) {
        pacchettiRicevuti <- cbind(pacchettiRicevuti, dstat$X.recv)
      }
      else {
        pacchettiRicevuti <- dstat$X.recv
      }
      if(exists("dstatMediaPacchettiRicevuti"))
        dstatMediaPacchettiRicevuti <- cbind(dstatMediaPacchettiRicevuti, mean(pacchettiRicevuti))
      else {
        dstatMediaPacchettiRicevuti <- cbind(mean(pacchettiRicevuti))
      }
      #PACCHETTI INVIATI
      if(exists("pacchettiInviati")) {
        pacchettiInviati <- cbind(pacchettiInviati, dstat$X.send)
      }
      else {
        pacchettiInviati <- dstat$X.send
      }
      if(exists("dstatMediaPacchettiInviati"))
        dstatMediaPacchettiInviati <- cbind(dstatMediaPacchettiInviati, mean(pacchettiInviati))
      else {
        dstatMediaPacchettiInviati <- cbind(mean(pacchettiInviati))
      }
      #THROUGHPUT RETE RICEVUTI
      if(exists("throughputReteRicevuti")) {
        throughputReteRicevuti <- cbind(throughputReteRicevuti, dstat$recv/1048576)
      }
      else {
        throughputReteRicevuti <- dstat$recv/1048576
      }
      if(exists("dstatMediaThroughputReteRicevuti"))
        dstatMediaThroughputReteRicevuti <- cbind(dstatMediaThroughputReteRicevuti, mean(throughputReteRicevuti))
      else {
        dstatMediaThroughputReteRicevuti <- cbind(mean(throughputReteRicevuti))
      }
      #THROUGHPUT RETE INVIATI
      if(exists("throughputReteInviati")) {
        throughputReteInviati <- cbind(throughputReteInviati, dstat$send/1048576)
      }
      else {
        throughputReteInviati <- dstat$send/1048576
      }
      if(exists("dstatMediaThroughputReteInviati"))
        dstatMediaThroughputReteInviati <- cbind(dstatMediaThroughputReteInviati, mean(throughputReteInviati))
      else {
        dstatMediaThroughputReteInviati <- cbind(mean(throughputReteInviati))
      }
    }
  }
  
  listMasterDstat = list(cpuMasterUsr,cpuMasterSys,cpuMasterIdl,cpuMasterWai,cpuMasterHiq,cpuMasterSiq,cpuMasterSomma,memoryMasterUsed, memoryMasterBuff, memoryMasterCach, memoryMasterFree,memoryMasterSomma,swapMaster, discoReadMaster, discoWriteMaster, 
                         pacchettiRicevutiMaster, pacchettiInviatiMaster,
                         throughputReteRicevutiMaster, throughputReteInviatiMaster)
  listDstat = list(cpuUsr,cpuSys, cpuIdl,cpuWai,cpuHiq,cpuSys,cpuSomma,memoryUsed, memoryBuff, memoryCach,memoryFree, memorySomma,swap, discoRead, discoWrite, pacchettiRicevuti, pacchettiInviati,
                   throughputReteRicevuti, throughputReteInviati)
  for(j in 1:19) {
    listDstat[[j]] <- cbind(listMasterDstat[[j]], listDstat[[j]])
    for(numeroColonna in 2:ncol(listDstat[[j]])) {
      variabile <- paste ("Slave", numeroColonna-1, sep = "", collapse = NULL)
      colnames(listDstat[[j]])[numeroColonna] <- variabile
    }
    listDstat[[j]] <- cbind(seq(delayDSTAT, nrow(listDstat[[j]])*delayDSTAT, delayDSTAT), listDstat[[j]])
    colnames(listDstat[[j]])[1] <- "Tempo"
    colnames(listDstat[[j]])[2] <- "Master"
  }
  
  dstatMedieCPUUsr <- cbind(dstatMediaCPUMasterUsr, dstatMediaCPUUsr)
  dstatMedieCPUSys <- cbind(dstatMediaCPUMasterSys, dstatMediaCPUSys)
  dstatMedieCPUIdl <- cbind(dstatMediaCPUMasterIdl, dstatMediaCPUIdl)
  dstatMedieCPUWai <- cbind(dstatMediaCPUMasterWai, dstatMediaCPUWai)
  dstatMedieCPUHiq <- cbind(dstatMediaCPUMasterHiq, dstatMediaCPUHiq)
  dstatMedieCPUSiq <- cbind(dstatMediaCPUMasterSiq, dstatMediaCPUSiq)
  dstatMedieCPUSomma <- cbind(dstatMediaCPUMasterSomma, dstatMediaCPUSomma)



  dstatMedieMemoriaUsed <- cbind(dstatMediaMemoriaMasterUsed, dstatMediaMemoriaUsed)
  dstatMedieMemoriaBuff <- cbind(dstatMediaMemoriaMasterBuff, dstatMediaMemoriaBuff)
  dstatMedieMemoriaCach <- cbind(dstatMediaMemoriaMasterCach, dstatMediaMemoriaCach)
  dstatMedieMemoriaFree <- cbind(dstatMediaMemoriaMasterFree, dstatMediaMemoriaFree)
  dstatMedieMemoriaSomma <- cbind(dstatMediaMemoriaMasterSomma, dstatMediaMemoriaSomma)


  dstatMedieSwap <- cbind(dstatMediaSwapMaster, dstatMediaSwap)
  dstatMedieDiscoRead <- cbind(dstatMediaDiscoReadMaster, dstatMediaDiscoRead)
  dstatMedieDiscoWrite <- cbind(dstatMediaDiscoWriteMaster, dstatMediaDiscoWrite)
  dstatMediePacchettiRicevuti <- cbind(dstatMediaPacchettiRicevutiMaster, dstatMediaPacchettiRicevuti)
  dstatMediePacchettiInviati <- cbind(dstatMediaPacchettiInviatiMaster, dstatMediaPacchettiInviati)
  dstatMedieThroughputReteRicevuti <- cbind(dstatMediaThroughputReteRicevutiMaster, dstatMediaThroughputReteRicevuti)
  dstatMedieThroughputReteInviati <- cbind(dstatMediaThroughputReteInviatiMaster, dstatMediaThroughputReteInviati)
  
  dstatMedieTotali <- rbind(dstatMedieCPUUsr,dstatMedieCPUSys,dstatMedieCPUIdl,dstatMedieCPUWai,dstatMedieCPUHiq,dstatMedieCPUSiq,dstatMedieCPUSomma, dstatMedieMemoriaUsed,dstatMedieMemoriaBuff,dstatMedieMemoriaCach,dstatMedieMemoriaFree,dstatMedieMemoriaSomma,dstatMedieSwap,dstatMedieDiscoRead,
                            dstatMedieDiscoWrite,dstatMediePacchettiRicevuti,dstatMediePacchettiInviati,
                            dstatMedieThroughputReteRicevuti,dstatMedieThroughputReteInviati)
  
  colnames(dstatMedieTotali)[1] <- "Master"
  dstatMediaNomi = rbind("CPU USR","CPU SYS","CPU IDL","CPU WAI","CPU HIQ","CPU SIQ", "CPU USR+SYS", "MEMORIA USED","MEMORIA BUFF","MEMORIA CACH","MEMORIA FREE", "MEMORIA USED+BUFF+CACH", "SWAP","DISCO LETTURA","DISCO SCRITTURA",
                         "PACCHETTI RETE RICEVUTI","PACCHETTI RETE INVIATI",
                         "THROUGHPUT RETE RICEVUTI","THROUGHPUT RETE INVIATI")
  dstatMedieTotali = cbind(dstatMediaNomi, dstatMedieTotali)
  colnames(dstatMedieTotali)[1] <- "Informazione"
  
  for(numeroColonna in 3:ncol(dstatMedieTotali)) {
    variabile <- paste ("Slave", numeroColonna-2, sep = "", collapse = NULL)
    colnames(dstatMedieTotali)[numeroColonna] <- variabile
  }
  output = paste(output, "excelDstat", sep="")

  l = list("CPU USR" = listDstat[[1]], "CPU SYS" = listDstat[[2]], "CPU IDL" = listDstat[[3]], "CPU WAI" = listDstat[[4]], "CPU HIQ" = listDstat[[5]], "CPU SIQ" = listDstat[[6]], "CPU USR + SYS" = listDstat[[7]], "MEMORIA USED" = listDstat[[8]],
           "MEMORIA BUFF" = listDstat[[9]], "MEMORIA CACH" = listDstat[[10]], "MEMORIA FREE" = listDstat[[11]], "MEMORIA USED + BUFF + CACH" = listDstat[[12]], 
           "SWAP"  = listDstat[[13]], "DISCO LETTURA" = listDstat[[14]], "DISCO SCRITTURA" =  listDstat[[15]], "PACCHETTI RICEVUTI"  = listDstat[[16]],
           "PACCHETTI INVIATI"  = listDstat[[17]], "THROUGHPUT RETE RICEVUTI" = listDstat[[18]], "THROUGHPUT RETE INVIATI" = listDstat[[19]], "Aggregato" = dstatMedieTotali)
  write.xlsx(x = l, file = output, colNames = TRUE)
}

result <- tryCatch({
	createXLS()
}, error = function(err){
  print(paste("MY_ERROR: ", err))
})
