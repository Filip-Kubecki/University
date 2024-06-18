library(calculus)
library(ggplot2)

dataset1 <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\29\\Dane\\Dane1.csv")
dataset2 <- read.csv("F:\\Projekty Intellij\\Text\\Fizyka3.1\\29\\Dane\\Dane2.csv")

# # functions
# createAvgRow <- function (data){
#   return(colMeans(data,na.rm = TRUE))
# }
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

ggplot(dataset1, aes(x=dataset1$T, y=dataset1$L)) +
  ggtitle("Zależność ΔL od ΔT - podgrzewanie próbki") +
  geom_point(fill="black", alpha=1) +
  # geom_errorbar(aes(x=dataset1$T, ymin=dataset1$L-dataset1$uL, ymax=dataset1$L+dataset1$uL), colour="red", alpha=0.9, size=0.2, width=0.2) +
  # geom_errorbar(aes(xmin=dataset1$T-dataset1$uT, xmax=dataset1$T+dataset1$uT), colour="red", alpha=0.9, size=0.4, width=0.1) +
  theme_linedraw() +
  scale_x_continuous(name = "ΔT [°C]",n.breaks = 10) +
  scale_y_continuous(name = "ΔL [um]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  )
  # geom_smooth(method = "lm",color ="turquoise4",size = 0.3)

ggplot(dataset2, aes(x=dataset2$T, y=dataset2$L)) +
  ggtitle("Zależność P od ΔT - ochładzanie próbki") +
  geom_point(fill="black", alpha=1) +
  geom_errorbar(aes(x=dataset2$T, ymin=dataset2$L-dataset2$uL, ymax=dataset2$L+dataset2$uL), colour="red", alpha=0.9, size=0.2, width=0.5) +
  geom_errorbar(aes(xmin=dataset2$T-dataset2$uT, xmax=dataset2$T+dataset2$uT), colour="red", alpha=0.9, size=0.4, width=0.5) +
  theme_linedraw() +
  scale_x_continuous(name = "ΔT [°C]",n.breaks = 10) +
  scale_y_continuous(name = "P [W]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  )+
  geom_smooth(method = "loess",color ="turquoise4",size = 0.3)

