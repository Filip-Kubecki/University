library(calculus)

# Data imports
Dane100aWałek <- read.csv("F:\\Projekty Intellij\\Text\\100a\\Dane100aWalek.csv")

# Functions
calculateAvg100aWalek <- function(data){
  avgVals <- colMeans(data,na.rm = TRUE)

  for (i in 1:7){
    exportAvgDataRow <- append(exportAvgDataRow,avgVals[i])
  }

  for (i in 8:13){
    exportAvgDataRow <- append(exportAvgDataRow, avgVals[i]/2)
  }
  exportAvgDataRow <- append(exportAvgDataRow, avgVals[14])
  return (exportAvgDataRow)
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
# Constants
uS <- uncertaintyB(0.05)
uM <- uncertaintyB(0.01)

# Main program
exportData <- data.frame(matrix(nrow = 0, ncol = 14))
exportAvgDataRow <- list()
uncertaintyARow <- list()
uncertaintyBRow <- list()
uncertaintyCRow <- list()

exportAvgDataRow <- calculateAvg100aWalek(Dane100aWałek)

exportData <- rbind(exportData, exportAvgDataRow)

colnames(exportData) <- c("Masa", "L1", "L2", "L3", "L4", "L5", "Lx", "r1", "r2", "r3", "r4", "r5", "rx", "V")

# Calculate type A uncertainty
uncertaintyARow <- NA
for (i in 2:13){
  uncertaintyARow <- append(uncertaintyARow, signif(uncertaintyA(Dane100aWałek, i),2))
}
uncertaintyARow <- append(uncertaintyARow, NA)
exportData <- rbind(exportData,uncertaintyARow)


# Calculate type b uncertainty
uncertaintyBRow <- append(uncertaintyBRow,signif(uM,2))
for (i in 2:7){
  uncertaintyBRow <- append(uncertaintyBRow,signif(uS,2))
}
for (i in 8:13){
  uncertaintyBRow <- append(uncertaintyBRow,signif(uM,2))
}
uncertaintyBRow <- append(uncertaintyBRow,signif(uncertaintyB(1),2))
exportData <- rbind(exportData,uncertaintyBRow)
# Calculate type C uncertainty
uncertaintyCRow <- append(uncertaintyCRow,signif(uM,2))
for (i in 2:7){
  uncertaintyCRow <- append(uncertaintyCRow, signif(uncertaintyC(uncertaintyA(Dane100aWałek, i),uS),2))
}
for (i in 8:13){
  uncertaintyCRow <- append(uncertaintyCRow, signif(uncertaintyC(uncertaintyA(Dane100aWałek, i),uM),2))
}
uncertaintyCRow <- append(uncertaintyCRow,signif(uncertaintyB(1),2))
exportData <- rbind(exportData,uncertaintyCRow)
# Objętość i niepewność objętości-------------------------------------------------------------

volumeFunction <- "L1*pi*r1^2+L2*pi*r2^2+L3*pi*r3^2+L4*pi*r4^2+L5*pi*r5^2-Lx*pi*rx^2"

varList <- exportData[1,2:13]

constList <- c(pi = pi)

uuncertaintyComplex2 <- uncertaintyComplex(volumeFunction,varList,constList,exportData[2,2:13])
# print(as.numeric(uuncertaintyComplex2))
# print(as.numeric(evaluate (volumeFunction,varList)))


print(exportData[4,2:13])
uuncertaintyComplex1 <- uncertaintyComplex(volumeFunction,varList,constList,exportData[4,2:13])
print(as.numeric(uuncertaintyComplex1))
print(as.numeric(evaluate (volumeFunction,varList)))

uuncertaintyComplex3 <- uncertaintyComplex("m/V",c(m=64.85,V=23200),c(),c(0.0058,34))
print(as.numeric(evaluate ("m/V",c(m=64.85,V=23200))))
print(as.numeric(uuncertaintyComplex3))

# Test
# volumeFunction <- "sqrt(x1^3)*x2^5"
#
# varList <- c(x1=1,x2=2)
#
# constList <- c(pi = pi)
#
# niepewnosc <- c(1,1)
#
#
# uuncertaintyComplexxxx <- uncertaintyComplex(volumeFunction,varList,constList,niepewnosc)
# print(uuncertaintyComplexxxx)
# evaluate (volumeFunction,varList)


# Write data to file
write.csv(exportData,"F:\\Projekty Intellij\\Text\\100a\\Dane100aWalekSrednieWartosci.csv")


