library(ggplot2)
library(calculus)

yellowDiodeData <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\48\\Dane\\YellowDiodeData.csv")
redDiodeData <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\48\\Dane\\RedDiodeData.csv")
greenDiodeData <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\48\\Dane\\GreenDiodeData.csv")
blueDiodeData <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\48\\Dane\\BlueDiodeData.csv")

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

# Użyte mierniki:
# - Sanwa CD771
# - Jenit JT890

# ggplot(yellowDiodeData,aes(x=U.V., y=I.mA.)) +
#   ggtitle("Zależność napięcia od natężenia - dioda żółta") +
#   geom_point(fill="black", alpha=1) +
#   theme_linedraw() +
#   scale_x_continuous(name = "Voltage[V]",n.breaks = 10) +
#   scale_y_continuous(name = "Current[mA]",n.breaks = 10) +
#   theme(plot.margin = unit(c(1,1,1,1), "cm"),
#         panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#         panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#         axis.title.x=element_text(size=12),
#         axis.title.y=element_text(size=12)
#   ) +
#   geom_smooth(method = "lm",se = FALSE,color ="red",size = 0.6,data = subset(yellowDiodeData,yellowDiodeData$U.V.>1.92))
#
# ggplot(greenDiodeData,aes(x=U.V., y=I.mA.)) +
#   ggtitle("Zależność napięcia od natężenia - dioda zielona") +
#   geom_point(fill="black", alpha=1) +
#   theme_linedraw() +
#   scale_x_continuous(name = "Voltage[V]",n.breaks = 10) +
#   scale_y_continuous(name = "Current[mA]",n.breaks = 10) +
#   theme(plot.margin = unit(c(1,1,1,1), "cm"),
#         panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#         panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#         axis.title.x=element_text(size=12),
#         axis.title.y=element_text(size=12)
#   ) +
#   geom_smooth(method = "lm",se = FALSE,color ="red",size = 0.6,data = subset(greenDiodeData,greenDiodeData$U.V.>1.9))
#
# ggplot(redDiodeData,aes(x=U.V., y=I.mA.)) +
#   ggtitle("Zależność napięcia od natężenia - dioda czerwona") +
#   geom_point(fill="black", alpha=1) +
#   theme_linedraw() +
#   scale_x_continuous(name = "Voltage[V]",n.breaks = 10) +
#   scale_y_continuous(name = "Current[mA]",n.breaks = 10) +
#   theme(plot.margin = unit(c(1,1,1,1), "cm"),
#         panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#         panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#         axis.title.x=element_text(size=12),
#         axis.title.y=element_text(size=12)
#   ) +
#   geom_smooth(method = "lm",se = FALSE,color ="red",size = 0.6,data = subset(redDiodeData,redDiodeData$U.V.>1.85))
#
# ggplot(blueDiodeData,aes(x=U.V., y=I.mA.)) +
#   ggtitle("Zależność napięcia od natężenia - dioda niebieska") +
#   geom_point(fill="black", alpha=1) +
#   theme_linedraw() +
#   scale_x_continuous(name = "Voltage[V]",n.breaks = 10) +
#   scale_y_continuous(name = "Current[mA]",n.breaks = 10) +
#   theme(plot.margin = unit(c(1,1,1,1), "cm"),
#         panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#         panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#         axis.title.x=element_text(size=12),
#         axis.title.y=element_text(size=12)
#   ) +
#   geom_smooth(method = "lm",se = FALSE,color ="red",size = 0.6,data = subset(blueDiodeData,blueDiodeData$U.V.>2.85))


# Regresion data
# Stałe
c <- 299792458
e <- 1.602176634e-19

print("RED")
x <- redDiodeData
regresion <- lm(x$I.mA.[37:length(x$I.mA.)]~x$U.V.[37:length(x$I.mA.)])
# plot(x$U.V.,x$I.mA.)
# abline(regresion, col = "red")
regresion <- summary(regresion)$coefficients

results <- c(
  regresion[1],
  regresion[2],
  regresion[3],
  regresion[4]
)
UB <- (regresion[1]/regresion[2])*(-1)
print(UB)

uncert <- uncertaintyComplex("-1*(b)/(a)",c(b = regresion[1],a = regresion[2]),c(),c(regresion[3],regresion[4]))
print("Uncertainty")
print(uncert)
l <- 6.3e-7
h <- ((e)/(c))*UB*l
cat("Czerwona planc",h)
uncertH <- uncertaintyComplex("(e)/(c)*UB*l",c(UB = UB,l = l),c(e = e,c = c),c(uncert,uncertaintyB(0.1e-7)))
print(uncertH)

print("BLUE")
x <- blueDiodeData
regresion <- lm(x$I.mA.[62:length(x$I.mA.)]~x$U.V.[62:length(x$I.mA.)])
# plot(x$U.V.,x$I.mA.)
# abline(regresion, col = "red")
# summary(regresion)$coefficients
regresion <- summary(regresion)$coefficients

results <- c(
  regresion[1],
  regresion[2],
  regresion[3],
  regresion[4]
)
print(results)
UB <- (regresion[1]/regresion[2])*(-1)
print(UB)
uncert <- uncertaintyComplex("-1*(b)/(a)",c(b = regresion[1],a = regresion[2]),c(),c(regresion[3],regresion[4]))
print("Uncertainty")
print(uncert)
l <- 4.6e-7
h <- ((e)/(c))*UB*l
cat("Niebieska planc",h)
uncertH <- uncertaintyComplex("(e)/(c)*UB*l",c(UB = UB,l = l),c(e = e,c = c),c(uncert,uncertaintyB(0.1e-7)))
print(uncertH)
print(uncertaintyB(0.1e-7))

print("YELLOW")
x <- yellowDiodeData
regresion <- lm(x$I.mA.[40:length(x$I.mA.)]~x$U.V.[40:length(x$I.mA.)])
# plot(x$U.V.,x$I.mA.)
# abline(regresion, col = "red")
# summary(regresion)$coefficients
regresion <- summary(regresion)$coefficients

results <- c(
  regresion[1],
  regresion[2],
  regresion[3],
  regresion[4]
)
UB <- (regresion[1]/regresion[2])*(-1)
print(UB)
uncert <- uncertaintyComplex("-1*(b)/(a)",c(b = regresion[1],a = regresion[2]),c(),c(regresion[3],regresion[4]))
print("Uncertainty")
print(uncert)
l <- 5.92e-7
h <- ((e)/(c))*UB*l
cat("Żółta planc",h)
uncertH <- uncertaintyComplex("(e)/(c)*UB*l",c(UB = UB,l = l),c(e = e,c = c),c(uncert,uncertaintyB(0.1e-7)))
print(uncertH)

print("GREEN")
x <- greenDiodeData
regresion <- lm(x$I.mA.[41:length(x$I.mA.)]~x$U.V.[41:length(x$I.mA.)])
# plot(x$U.V.,x$I.mA.)
# abline(regresion, col = "red")
# summary(regresion)$coefficients
regresion <- summary(regresion)$coefficients

results <- c(
  regresion[1],
  regresion[2],
  regresion[3],
  regresion[4]
)
UB <- (regresion[1]/regresion[2])*(-1)
print(UB)
uncert <- uncertaintyComplex("-1*(b)/(a)",c(b = regresion[1],a = regresion[2]),c(),c(regresion[3],regresion[4]))
print("Uncertainty")
print(uncert)
l <- 5.6e-7
h <- ((e)/(c))*UB*l
cat("Zielona planc",h)
uncertH <- uncertaintyComplex("(e)/(c)*UB*l",c(UB = UB,l = l),c(e = e,c = c),c(uncert,uncertaintyB(0.1e-7)))
print(uncertH)
