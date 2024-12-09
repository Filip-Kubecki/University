library(ggplot2)


options(scipen=999)

theme <- theme(
  plot.margin = unit(c(1,1,1,1), "cm"),
  panel.grid.major.y = element_line(colour = "black", linetype = "dashed", size=0.1),
  # panel.grid.minor.y = element_line(colour = "black", linetype = "dashed"),
  panel.grid.minor.x = element_blank(),
  panel.grid.minor.y = element_blank(),
  plot.title = element_text(size=18),
  axis.text.x = element_text(size = 12),
  axis.text.y = element_text(size = 12),
  axis.title.x=element_text(size=20),
  axis.title.y=element_text(size=20),
  legend.title = element_text(size = 10,face ="bold"),
  legend.background = element_rect(fill="white",
                                   size=0.2, linetype="solid",
                                   colour ="black"),
  legend.text.align = 1
)

regressionValues <- function(regression, round=NA){
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
  returnData <- data.frame(
    "Slope" = slope,
    "Intercept" = intercept,
    "R^2" = R2,
    "Adjusted R^2" = adjR2
  )
  return(returnData)
}

regressionEqText <- function(regression, round=NA){
  return(
    paste("y=",regressionValues(regression, round)$Slope,"x+",regressionValues(regression,round)$Intercept)
  )
}

ref <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab6/Data/refData.csv")
dane <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab6/Data/data.csv")

daneC <- dane
daneC$xco2 <- as.numeric(daneC$xco2)
daneC$U <- as.numeric(daneC$U)
dane <- dane[-3,]
dane$xco2 <- as.numeric(dane$xco2)
dane$U <- as.numeric(dane$U)


regV <- regressionEqText(reg, 4)
regV

slope <- -25.421
intercept <- 524.9978

nco2 <- (daneC$No2CO3)/1000
daneC$nco2 <- nco2
vco2 <- (nco2*8.314*(ref$T.C.+273.15))/(ref$P.hPa.*100)
xco2 <- (vco2)/(0.00275)*10^6
daneC$xco2.2 <- xco2
pco2 <- (xco2/10^6)*ref$P.hPa.*100
daneC$pco2 <- pco2
daneC$U <- daneC$U
daneC$U

reg <- lm(daneC$U~log(daneC$pco2))
plot(log(daneC$pco2), daneC$U,
     main="BLIP BLOP"
)
abline(reg)
rV <- regressionValues(reg, 4)
rV
daneC
daneC$xco2 -daneC$xco2.2

outputData <- c(daneC$xco2)
outputData <- data.frame(
  "Xco2" = daneC$xco2,
  "Nco2" = daneC$nco2,
  "Xco2.2" = daneC$xco2.2,
  "Pco2" = daneC$pco2
)
outputData$Xco2.2 <- round(outputData$Xco2.2, 2)
outputData$Pco2 <- round(outputData$Pco2, 2)


ggplot(data = daneC, aes(y=daneC$U, x=log(daneC$pco2))) +
  geom_point(size=2) +
  # geom_smooth(method="lm", aes(y=(rData$Rb.kOhm.),x=rData$rT.K),fullrange=TRUE,color="black") +
  ggtitle("Krzywa kalibracyjna czujnika TGS4160") +
  theme_linedraw() +
  scale_x_continuous(name = "log(P_co2)",n.breaks = 8) +
  scale_y_continuous(name = "SEM [mV]",
                     # trans="log10",
                     # breaks = trans_breaks("log10", function(x) 10^x),
                     # labels = trans_format("log10", math_format(10^.x))
                     n.breaks = 22
  ) +
  theme +
  geom_label(aes(y=335, x=5.7, label=paste("y=",rV$Slope,"x+",rV$Intercept)), hjust=0, size = 10) +
  geom_label(aes(y=330, x=5.7, label=paste("R^2: ",rV$R.2)), hjust=0, size = 10) +
  geom_smooth(method="lm",color="black", fullrange=TRUE)

ggsave(
  "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab6/Img/plot1.png",
  units="mm",
  width = 297,
  height = 210,
  dpi = 300
)

# write.csv(outputData,"/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab6/Data/output.csv")
