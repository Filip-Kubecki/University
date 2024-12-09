"NEW RUN ----------------------------------------------"

p10and90 <- function(data){
  maxValue <- data[which.max(data$Value),]$Value
  minValue <- data[which.min(data$Value),]$Value
  # Add finding 10% and 90% value and from it point
  range <- maxValue-minValue
  v90 <- (range*0.9)+minValue
  v10 <- (range*0.1)+minValue

  p10 <- which(data$Value < v10+100 & data$Value > v10-100, arr.ind=TRUE)
  p90 <- which(data$Value < v90+100 & data$Value > v90-1000, arr.ind=TRUE)
  print(p90)
  return(c(data[p10[1],], data[p90[1],],data[p10[2],], data[p90[length(p90)],]))
}


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
  return(c( slope,  intercept,R2, adjR2))
}

# ------------------------------------------------------------------------------------------
# ALCOHOL CALCULATIONS ------------------------------------------------------------------
# ------------------------------------------------------------------------------------------

ref <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Data/ref.csv")
alcData <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Data/alcData.csv")

alcData$G <- 1/(alcData$R.KOhm.*1000) # Adding Conductance column
g0 <- alcData$G[1]

alcData$S <- alcData$G/g0
alcDataO <- alcData
alcDataO$G <- format(round(alcDataO$G, 6),scientific = FALSE)
alcDataO$S <- round(alcDataO$S, 3)

write.csv(alcDataO, "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Data/output.csv")

alcDataPlot <- alcData[-c(8,7,1),]

alcDataPlot$xgaz.ppm. <- as.numeric(alcDataPlot$xgaz.ppm.)
alcDataPlot$G <- as.numeric(alcDataPlot$G)
alcDataPlot$S <- as.numeric(alcDataPlot$S)



# ------------------------------------------------------------------------------------------
# RESISTIVE SENSOR -------------------------------------------------------------------------
# ------------------------------------------------------------------------------------------


regG <- lm((alcDataPlot$G)~log(alcDataPlot$xgaz.ppm.))
slope <- regValues(regG)[1]
intercept <- regValues(regG)[2]
R2 <- regValues(regG)[3]
png(
  filename = "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Img/resistanceG=f(x).png",
  width = 1300,
  height = 750
)
plot(alcDataPlot$xgaz.ppm., alcDataPlot$G,
     cex=1.8,
     pch=19,
     main="Czujnik rezystancyjny - G=f(x)",
     xlab="x_gaz [ppm]",
     ylab="G [S]",
     cex.main=2,
     cex.lab=1.6,
     axes=FALSE,
     frame=TRUE
)
axis(1, at=seq(0,1000,by=100), tck=1,lty="aa", col="gray",cex.axis=1.4)
axis(2, at=seq(0, 0.0005,by=0.00005), tck=1, lty="aa", col="gray",cex.axis=1.4)
x <- seq(from=1,to=1000,length.out=100)
y <- intercept+(slope*log(x))
lines(x, y, lty="aa", lwd=3)
text(
  200,
  0.00043,
  paste("R²=", round(R2,4)),
  cex=2.4
)
text(
  220,
  0.00045,
  paste("y=", format(round(intercept, 6), scientific = FALSE),"+", format(round(slope,6),scientific = FALSE),"*log(x)"),
  cex=2.4
)
dev.off()
alcData$G[7]
exp((alcData$G[7]-intercept)/slope)



regG <- lm(alcDataPlot$S~alcDataPlot$xgaz.ppm.)
png(
  filename = "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Img/resistanceS=f(x).png",
  width = 1300,
  height = 750
)
plot(alcDataPlot$xgaz.ppm., alcDataPlot$S,
     cex=1.8,
     pch=19,
     main="Czujnik rezystancyjny - S=f(x)",
     xlab="x_gaz [ppm]",
     ylab="S",
     cex.main=2,
     cex.lab=1.6,
     axes=FALSE,
     frame=TRUE
)
axis(1, at=seq(100,1000,by=100), tck=1,lty="aa", col="gray",cex.axis=1.4)
axis(2, at=seq(5, 16,by=1), tck=1, lty="aa", col="gray",cex.axis=1.4)
dev.off()
# ------------------------------------------------------------------------------------------
# AMPEROMETRIC SENSOR ----------------------------------------------------------------------
# ------------------------------------------------------------------------------------------

png(
  filename = "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Img/amperometricimax=f(x).png",
  width = 1300,
  height = 750
)
regression <- (lm(alcDataPlot$imax.uA.~alcDataPlot$xgaz.ppm.))
plot(alcDataPlot$xgaz.ppm., alcDataPlot$imax.uA.,
     cex=1.8,
     pch=19,
     main="Czujnik amperometryczny - I_max=f(x)",
     xlab="x_gaz [ppm]",
     ylab="I_max [µA]",
     cex.main=2,
     cex.lab=1.6,
     axes=FALSE,
     frame=TRUE
)
axis(1, at=seq(100,1000,by=100), tck=1,lty="aa", col="gray",cex.axis=1.4)
axis(2, at=seq(0, 900,by=100), tck=1, lty="aa", col="gray",cex.axis=1.4)
abline(regression)
# dev.off()
regV <- regValues(regression)
text(
  250,
  800,
  paste("y=", format(round(regV[1], 5), scientific = FALSE),"x+", format(round(regV[2],4),scientific = FALSE)),
  cex=2.4
)
text(
  250,
  750,
  paste("R²=", round(regV[3],4)),
  cex=2.4
)
dev.off()

"Amperometric alc [ppm]:"
(alcData$imax.uA.[7]-regV[2])/regV[1]




# ------------------------------------------------------------------------------------------
# MULTIMETER CALCULATIONS ------------------------------------------------------------------
# ------------------------------------------------------------------------------------------

data <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Data/multimeterData.csv")
data$Mode <- NULL
data$Value <- as.numeric(data$Value)
data$Time.s. <- as.numeric(data$Time.s.)

dataRise <- subset(data, data$Time.s. > 2100 & data$Time.s. < 2534)
maxValue <- dataRise[which.max(dataRise$Value),]
minValue <- dataRise[which.min(dataRise$Value),]

png(
  filename = "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab4/Img/resistanceSlope.png",
  width = 1300,
  height = 750
)
plot(
  dataRise$Time.s.,
  dataRise$Value,
  pch=20,
  xlab="Time [s]",
  cex.lab=1.5,
  ylab="",
  cex=1.4,
  main="Czujnik Rezystancyjny - odpowiedź, powrót",
  xaxt="n"
)
axis(1, at=seq(2100,2550,by=50), tck=1,lty="aa", col="gray")
# axis(2, at=seq(10000, 33000, by=20000), tck=1, lty="aa", col="gray",axt="n")

out <- p10and90(dataRise)
# P10 rise
abline(v=out[1],lty="aa", lwd=2)
abline(h=out[2],lty="aa", lwd=2)
# P90 rise
abline(v=out[3],lty="aa", lwd=2)
abline(h=out[4],lty="aa", lwd=2)
text(
  2500,
  32000,
  "Y*90%",
  cex=2
)
# P10 fall
abline(v=out[5],lty="aa", lwd=2)
abline(h=out[6],lty="aa", lwd=2)
text(
  2500,
  8000,
  "Y*10%",
  cex=2
)
# P90 fall
abline(v=out[7],lty="aa", lwd=2)

out <- as.numeric(out)
riseTime <- out[3]-out[1]
text(
  2145,
  30500,
  paste("Rise_t=",round(riseTime,2),"s"),
  cex=1.4
)
fallTime <- out[5]-out[7]
text(
  2370,
  30500,
  paste("Fall_t=",round(fallTime,2),"s"),
  cex=1.4
)

dev.off()
