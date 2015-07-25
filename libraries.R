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
loadLibrary("colorspace")
loadLibrary("labeling")
loadLibrary("scales")
loadLibrary("ggplot2")
loadLibrary("plyr")
loadLibrary("reshape2")
loadLibrary("XML")
loadLibrary("intervals")
loadLibrary("openxlsx")
loadLibrary("methods")