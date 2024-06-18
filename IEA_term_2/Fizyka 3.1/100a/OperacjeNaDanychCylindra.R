library(calculus)
daneCylindra <- read.csv("F:\\Projekty Intellij\\Text\\100a\\Dane100aCylinder.csv")

# functions
createAvgRow <- function (data){
  return(colMeans(data,na.rm = TRUE))
}
uncertaintyA <- function(data, colNum){
  return ((sd(data[,colNum]))/(sqrt(length(data[,colNum]))))
}
uncertaintyB <- function(value){
  return (sqrt((value)^2/(3)))
}
uncertaintyC <- function(uA,uB){
  return (sqrt(uA^2+uB^2))
}
uncertaintyComplex <- function(mathFunction,variableList,constList,uncertaintyList){
  output <- double()
  derivatives <- derivative(mathFunction,variableList,constList)
  for (i in seq_along(variableList)){
    output <- sum(as.double((derivatives[1,i]^2*uncertaintyList[i]^2)),output)
  }
  return (sqrt(output))
}

# variables
exportData <- data.frame(matrix(nrow = 0, ncol = 4))

uS <- uncertaintyB(0.05)
uM <- uncertaintyB(0.01)

# Avg values and diameter changed to radius
exportData <- rbind(exportData,createAvgRow(daneCylindra))
colnames(exportData) <- c("Masa","L","R","r","V")
# print(exportData)
exportData$R <- (exportData$R/2)
exportData$r <- (exportData$r/2)

# Uncertainty
volumeFunction <- "L*pi*(R^2-r^2)"

varList <- exportData[1,2:4]

constList <- c(pi = pi)
uncertaintyCrow <- c(
  uncertaintyC(uncertaintyA(daneCylindra,2),uncertaintyB(uS)),
  uncertaintyC(uncertaintyA(daneCylindra/2,3),uncertaintyB(uM)),
  uncertaintyC(uncertaintyA(daneCylindra/2,4),uncertaintyB(uM))
)

uncA <- c(
  uncertaintyA(daneCylindra,2),
  uncertaintyA(daneCylindra/2,3),
  uncertaintyA(daneCylindra/2,4)
)
print(uncA)
# print(varList)
# print(constList)
# print(uncertaintyCrow)

x <- uncertaintyComplex(volumeFunction,varList,constList,uncertaintyCrow)
print(as.double(x))


# Export data. End of code
# write.csv(exportData,"F:\\Projekty Intellij\\Text\\100a\\Dane100aCylinderSrednieWartoscixxx.csv")
# print("done")