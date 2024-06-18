library(calculus)
library(ggplot2)

dataset <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\44a\\Dane\\Dane.csv")

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

# # Próbka 1
# plot(
#      dataset$X,
#      dataset$X.1,
#      main="Zależność rezystancji od temperatury - próbka 1",
#      ylab="Resistance [Ohm]",
#      xlab="Temp [°C]",
#      pch=20,
# )
# grid(nx = NULL, ny = NULL,
#      lty = 3,      # Grid line type
#      col = "gray", # Grid line color
#      lwd = 1)      # Grid line width
# # Próbka 2
# plot(
#   dataset$X,
#   dataset$X.2,
#   main="Zależność rezystancji od temperatury - próbka 2",
#   ylab="Resistance [Ohm]",
#   xlab="Temp [°C]",
#   pch=20,
# )
# grid(nx = NULL, ny = NULL,
#      lty = 3,      # Grid line type
#      col = "gray", # Grid line color
#      lwd = 1)      # Grid line width
# # Próbka 3
# plot(
#   dataset$X,
#   dataset$X.3,
#   main="Zależność rezystancji od temperatury - próbka 4",
#   ylab="Resistance [Ohm]",
#   xlab="Temp [°C]",
#   pch=20,
# )
# grid(nx = NULL, ny = NULL,
#      lty = 3,      # Grid line type
#      col = "gray", # Grid line color
#      lwd = 1)      # Grid line width
# # Próbka 4
# plot(
#   dataset$X,
#   dataset$X.4,
#   main="Zależność rezystancji od temperatury - próbka 4",
#   ylab="Resistance [Ohm]",
#   xlab="Temp [°C]",
#   pch=20,
# )
# model4LinearReg <- lm(X.4~X,data = dataset)
# summary(model4LinearReg)$coefficient
# abline(model4LinearReg,lty=1, lwd=0.5,col="red")
# grid(nx = NULL, ny = NULL,
#      lty = 3,      # Grid line type
#      col = "gray", # Grid line color
#      lwd = 1)      # Grid line width
#
# # Niepewnosć złożona
# # f <- "a/b"
# # u <- uncertaintyComplex(f,c(a=0.3855,b=100.8492),c(),c(0.002384896,0.163355221))
# # u
#
# # Próbka 4
# T <- 1000/(dataset$X+273.15)
# lnRs <- log(dataset$X.2)
# plot(
#   T,
#   lnRs,
#   # main="Zależność rezystancji od temperatury - próbka 2",
#   ylab="ln(R_s) [Ohm]",
#   xlab="1000/T [1/K]",
#   pch=20,
# )
# model2LinearReg <- lm(lnRs~T)
# summary(model2LinearReg)$coefficient
# abline(model2LinearReg,lty=1, lwd=0.5,col="red")
# grid(nx = NULL, ny = NULL,
#      lty = 3,      # Grid line type
#      col = "gray", # Grid line color
#      lwd = 1)      # Grid line width


ggplot(dataset,aes(x=X, y=X.1)) +
  ggtitle("Zależność rezystancji od temperatury - próbka 1") +
  geom_point(fill="black", alpha=1) +
  geom_errorbar( aes(x=X, ymin=X.1-X.5, ymax=X.1+X.5), colour="red", alpha=0.9, size=0.7,width=0.7) +
  geom_errorbar( aes(xmin=X-X.9, xmax=X+X.9), colour="red", alpha=0.9, size=0.7,width=0.7) +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature[°C]",n.breaks = 10) +
  scale_y_continuous(name = "Resistance[Ω]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  )

ggplot(dataset,aes(x=X, y=X.2)) +
  ggtitle("Zależność rezystancji od temperatury - próbka 2") +
  geom_point(fill="black", alpha=1) +
  geom_errorbar( aes(x=X, ymin=X.2-X.6, ymax=X.2+X.6), colour="red", alpha=0.9, size=0.7,width=0.7) +
  geom_errorbar( aes(xmin=X-X.9, xmax=X+X.9), colour="red", alpha=0.9, size=0.7,width=0.7) +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature[°C]",n.breaks = 10) +
  scale_y_continuous(name = "Resistance[Ω]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  )

ggplot(dataset,aes(x=X, y=X.3)) +
  ggtitle("Zależność rezystancji od temperatury - próbka 3") +
  geom_point(fill="black", alpha=1) +
  geom_errorbar( aes(x=X, ymin=X.3-X.7, ymax=X.3+X.7), colour="red", alpha=0.9, size=0.7,width=0.7) +
  geom_errorbar( aes(xmin=X-X.9, xmax=X+X.9), colour="red", alpha=0.9, size=0.7,width=0.7) +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature[°C]",n.breaks = 10) +
  scale_y_continuous(name = "Resistance[Ω]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  )

ggplot(dataset,aes(x=X, y=X.4)) +
  ggtitle("Zależność rezystancji od temperatury - próbka 4") +
  geom_point(fill="black", alpha=1) +
  geom_errorbar( aes(x=X, ymin=X.4-X.8, ymax=X.4+X.8), colour="red", alpha=0.9, size=0.7,width=0.7) +
  geom_errorbar( aes(xmin=X-X.9, xmax=X+X.9), colour="red", alpha=0.9, size=0.7,width=0.7) +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature[°C]",n.breaks = 10) +
  scale_y_continuous(name = "Resistance[Ω]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  )


ggplot(dataset,aes(x=X, y=X.4)) +
  ggtitle("Zależność rezystancji od temperatury - próbka 4 z regresją liniową") +
  geom_point(fill="black", alpha=1) +
  geom_errorbar( aes(ymin=X.4-X.8, ymax=X.4+X.8), colour="red", alpha=0.9, size=0.7,width=0.7) +
  geom_errorbar( aes(xmin=X-X.9, xmax=X+X.9), colour="red", alpha=0.9, size=0.7,width=0.7) +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature[°C]",n.breaks = 10) +
  scale_y_continuous(name = "Resistance[Ω]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  ) +
  geom_smooth(method = "lm",color ="turquoise4",size = 0.4)


T <- 1000/(dataset$X+273.15)
ucT <- sqrt((((-1000)/((dataset$X+273.15)^2))*uncertaintyB(0.1))^2)
lnRs <- log(dataset$X.2)
uclnRs <-(dataset$X.6/dataset$X.2)
# model2LinearReg <- lm(lnRs~T)
# summary(model2LinearReg)$coefficient
# print("XXXXXXX")
# model4LinearReg <- lm(X.4~X,data = dataset)
# summary(model4LinearReg)$coefficient

ggplot(dataset,aes(x=T, y=lnRs)) +
  ggtitle("Zależność ln(R_s) od 1000/T - próbka 2 z regresją liniową") +
  geom_point(fill="black", alpha=1) +
  geom_errorbar( aes(ymin=lnRs-uclnRs, ymax=lnRs+uclnRs), colour="red", alpha=0.9, size=0.4,width=0.5) +
  geom_errorbar( aes(xmin=T-ucT, xmax=T+ucT), colour="red", alpha=0.9, size=0.4) +
  theme_linedraw() +
  scale_x_continuous(name = "1000/T[1/K]",n.breaks = 10) +
  scale_y_continuous(name = "ln(R_s)[Ω]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  ) +
  geom_smooth(method = "lm",color ="turquoise4",size = 0.4)

T
ucT





