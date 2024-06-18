library(calculus)

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

gMatematyczne <- "4*pi^2*((l)/(T^2))"
variables <- c(l =0.16,T = 0.7836)
constList <- c(pi = pi)
uncertainty <- c(0.001154701,0.01586683)

print("Nowe obliczenie g")
# print(derivative(gMatematyczne,var = "T"))
evaluate(gMatematyczne,variables,constList)
x <- uncertaintyComplex(gMatematyczne,variables,constList,uncertainty)
x


# `functionI0` <- "(1/8)*m*(d^2+D^2)"
# `functionI` <- "I+m*((d^2)/(4))"
#
# `function` <- "8*pi^2*((I)/(T^2*m*d))"
# variables <- c(I = 0.002307054,T = 0.7408,m = 0.264, d = 0.1295)
# constList <- c(pi = pi)
# uncertainty <- c(
#   7.09E-07
#   ,0.01496283
#   ,5.7735E-05
#   ,2.88675E-05
# )
#
# # print(derivative(gMatematyczne,var = "T"))
# evaluate(`function`,variables,constList)
# x <- uncertaintyComplex(`function`, variables, constList, uncertainty)
# x


