# library(calculus)
#
# uncertaintyA <- function(data, colNum){
#   return ((sd(data[,colNum]))/(sqrt(length(data[,colNum]))))
# }
# uncertaintyB <- function(value){
#   return (sqrt((value)^2/(3)))
# }
# uncertaintyC <- function(uA,uB){
#   return (sqrt(uA^2+uB^2))
# }
# uncertaintyComplex <- function(mathFunction,variableList,constList,uncertaintyList){
#   output <- double()
#   derivatives <- derivative(mathFunction,variableList,constList)
#   for (i in seq_along(variableList)){
#     output <- sum(as.double((derivatives[1,i]^2*uncertaintyList[i]^2)),output)
#   }
#   return (sqrt(output))
# }
#
# funkcja <- "V/a"
# variables <- c(a = 0.0405441176470588,V = -0.17)
# uncertainty <- c(0.000315675,uncertaintyB(0.020051))
#
# uncertaintyB(0.02018)
#
#
#
# c <- uncertaintyComplex(funkcja,variables,c(),uncertainty)
# c

data <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\20a\\Dane\\DaneWykres.csv")
plot(data,
     xlab = "Time[s]",
     ylab = "Voltage[mV]")