library(ggplot2)
# theme <- theme(
#   plot.margin = unit(c(1,1,1,1), "cm"),
#   panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#   # panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#   axis.text.x = element_text(size = 12),
#   axis.text.y = element_text(size = 12),
#   axis.title.x=element_text(size=16),
#   axis.title.y=element_text(size=16),
#   legend.title = element_text(size = 10,face ="bold"),
#   legend.background = element_rect(fill="white",
#                                    size=0.2, linetype="solid",
#                                    colour ="black"),
#   legend.text.align = 1
# )

regValues <- function(regression, round=NA){
  intercept<-(summary(regression)$coefficients[1,1]) # intercept value
  slope<-(summary(regression)$coefficients[2,1])     # slope value
  R2 <- summary(regression)$r.squared                # R^2 value
  adjR2 <- summary(regression)$adj.r.squared         # adjusted R^2 value
  if (!is.na(round)){
    intercept <- round(intercept, round)
    slope <- round(slope, round)
    R2 <- round(R2, round)
    adjR2 <- round(adjR2, round)
  }
  return(c(slope, intercept, R2, adjR2))
}

data <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab5/Dane/multimeterData.csv")

RHData <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab5/Dane/RHdata.csv")
RHCOPY <- RHData

T <- 24.8
pn <- (0.61121*exp((18.678-(T)/(234.5))*((T)/(257.14+T))))
pw <- ((RHData$RHconf*pn)/100)*1000
pw <- round(pw, 3)
outputData <- cbind(RHData, pw)
# outputData
write.csv(outputData, "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab5/Dane/output.csv")


png(
  filename = "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab5/Img/capCRH.png",
  width = 1300,
  height = 750
)
RHData <- RHData[-c(1),]
# CAPACITANCE
regression <- lm(RHData$cap~RHData$RHre)
# "CAPACITY REGRESSION ___"
plot(RHData$RHre, RHData$cap,
     pch = 20,
     cex = 1.6,
     xlab="Humidity [%]",
     ylab="Capacitance [nF]",
     cex.lab=1.3
)
title(main = "Czujnik pojemnościowy - C=f(RH)", cex.main=2)
grid(nx = NULL, ny = NULL,
     lty = 2,      # Grid line type
     col = "gray", # Grid line color
     lwd = 2
)
reg <- regValues(regression)
# text(30, 0.174, "y=0.0001813x+0.1608843", cex=2)
# text(30, 0.1735, "R^2=0.9929", cex=2)

text(25, 0.174, paste("y=",format(round(reg[1], 7),scientific = FALSE),"x+",round(reg[2],4)), cex=2)
text(25, 0.1735, paste("R^2=",round(reg[4],4)), cex=2)
abline(regression, lty="aa")
dev.off()


png(
  filename = "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab5/Img/impCRH.png",
  width = 1300,
  height = 750
)
# IMPEDANCE log model
regression <- lm(log(RHData$imp)~(RHData$RHre))
# "IMPEDANCE REGRESSION ___ log type"
intercept<-(summary(regression)$coefficients[1,1]) # intercept
slope<-(summary(regression)$coefficients[2,1]) # slope
R2 <- summary(regression)$r.squared
adjR2 <- summary(regression)$adj.r.squared

plot(RHData$RHre, RHData$imp,
  pch = 20,cex=1.6,
  xlab="Humidity [%]",
  ylab="Capacitance [nF]",
     cex.lab=1.3
)
title(main = "Czujnik impedancyjny - C=f(RH)", cex.main=2)
grid(nx = NULL, ny = NULL,
  lty = 2,      # Grid line type
  col = "gray", # Grid line color
  lwd = 2
)
text(25, 8, paste("y=",round(exp(intercept),5),"*",round(exp(slope),3),"^x"), cex=2)
text(25, 7.5, paste("R^2=",round(R2,4)), cex=2)

x<-seq(from=1,to=100,length.out=50)
y<-(exp(intercept))*((exp(slope))^x)
exp(intercept)
exp(slope)
lines(x, y, lty="aa")
dev.off()

# -------------------------------------------------------------------

png(
  filename = "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab5/Img/impLogCRH.png",
  width = 1300,
  height = 750
)
# IMP LOG C
regression <- lm(log(RHData$imp)~RHData$RHre)
"IMPEDANCE REGRESSION ___"
plot(RHData$RHre, log(RHData$imp),
     pch = 20,
     cex=1.6,
     xlab="Humidity [%]",
     ylab="Capacitance [nF]",
     cex.lab=1.3
)
title(main = "Czujnik impedancyjny - log(C)=f(RH)", cex.main=2)
grid(nx = NULL, ny = NULL,
     lty = 2,      # Grid line type
     col = "gray", # Grid line color
     lwd = 2
)
abline(regression, lty="aa")
reg <- regValues(regression)
text(25, 1.4, paste("y=",round(reg[1], 4),"x",round(reg[2],4)), cex=2)
text(25, 1, paste("R^2=",round(reg[4],4)), cex=2)
# SAVE PLOT AS PHOTO
dev.off()

# -------------------------------------------------------------------

# # CAPACITANCE ----------------------------------_SDLFKJSDDLFKJLSDKFJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ
# dataFilter <- subset(data, data$Time.s. > 70 & data$Time.s. < 2100)
# dataFilter <- dataFilter[!(dataFilter$Value>1.76e-10),]
# dataCap <- dataFilter[!(dataFilter$Value<0),]
#
# plot(dataCap$Time.s., dataCap$Value,
#      pch = 20
# )
#
# # IMPEDANCYJNY
# dataImp <- subset(data, data$Time.s. > 2400 & data$Time.s. < 2910)
# dataImp <- dataImp[!(dataImp$Value>9e-9),]
#
# # Regression -rissing
# regressionData <- subset(dataImp, dataImp$Time.s. > 2460 & dataImp$Time.s. < 2700)
# regression <- lm(regressionData$Value~regressionData$Time.s.)
#
# plot(dataImp$Time.s., as.numeric(dataImp$Value),
#      pch = 20,
#      xlab="Time [s]",
#      ylab="Capacitance [F]",
#      main = "Czujnik Impedancyjny - czas narastania",
#      cex.main=2
# )
# grid(nx = NULL, ny = NULL,
#      lty = 2,      # Grid line type
#      col = "gray", # Grid line color
#      lwd = 2)      # Grid line width
#
# minTime <- 2460
# minIndex <- which(dataImp$Time.s. < minTime & dataImp$Time.s. > minTime-1, arr.ind=TRUE)
# minValue <- dataImp$Value[minIndex[1]]
#
# maxTime <- 2714
# maxIndex <- which(dataImp$Time.s. < maxTime & dataImp$Time.s. > maxTime-1, arr.ind=TRUE)
# maxValue <- dataImp$Value[maxIndex[1]]
# minValue <- as.numeric(minValue)
# maxValue <- as.numeric(maxValue)
# valueRange <- maxValue-minValue
# p10 <- minValue+(valueRange*0.1)
# p90 <- minValue+(valueRange*0.9)
# p10
# p10index <- which(dataImp$Value < p10+1 & dataImp$value > p10-1, arr.ind=TRUE)
# p10index
# p10T <- dataImp$Time.s.[p10index[1]]
# p10T
#
# abline(v=2645,col="red", lwd=2, lty='aa')
# abline(v=2472,col="blue", lwd=2, lty='aa')
# abline(h=p90,col="red", lwd=3, lty='aa')
# abline(h=p10,col="blue", lwd=3, lty='aa')
# 173/60
# 173%%60
# text(2555, 3.8e-10, "Czas odpowiedzi: 173 s = 2 min 53 s", cex=1.4)
# text(2426,0.6e-10,"Y0+0.1(Yk-Y0)",cex=1.9)
# text(2426,3.6e-10,"Y0+0.9(Yk-Y0)",cex=1.9)
#
#
# "_______________________--"
#
# plot(dataImp$Time.s., as.numeric(dataImp$Value),
#      pch = 20,
#      xlab="Time [s]",
#      ylab="Capacitance [F]",
#      main = "Czujnik Impedancyjny - czas powrotu",
#      cex.main=2
# )
# grid(nx = NULL, ny = NULL,
#      lty = 2,      # Grid line type
#      col = "gray", # Grid line color
#      lwd = 2)      # Grid line width
#
# minTime <- 2460
# minIndex <- which(dataImp$Time.s. < minTime & dataImp$Time.s. > minTime-1, arr.ind=TRUE)
# minValue <- dataImp$Value[minIndex[1]]
#
# maxTime <- 2714
# maxIndex <- which(dataImp$Time.s. < maxTime & dataImp$Time.s. > maxTime-1, arr.ind=TRUE)
# maxValue <- dataImp$Value[maxIndex[1]]
# minValue <- as.numeric(minValue)
# maxValue <- as.numeric(maxValue)
# valueRange <- maxValue-minValue
# p10 <- minValue+(valueRange*0.22)
# p90 <- minValue+(valueRange*0.9)
# p10index <- which(dataImp$Value < 2900+3 & dataImp$value > 2900-2, arr.ind=TRUE)
# p10T <- dataImp$Time.s.[p10index[1]]
#
# abline(v=2645,col="red", lwd=2, lty='aa')
# abline(v=2900,col="blue", lwd=2, lty='aa')
# abline(h=p90,col="red", lwd=3, lty='aa')
# abline(h=p10,col="blue", lwd=3, lty='aa')
# text(2800, 3.8e-10, "Czas odpowiedzi: 255 s = 4 min 15 s", cex=1.4)
# text(2426,1.1e-10,"Y0+0.22(Yk-Y0)",cex=1.9)
# text(2426,3.6e-10,"Y0+0.9(Yk-Y0)",cex=1.9)
