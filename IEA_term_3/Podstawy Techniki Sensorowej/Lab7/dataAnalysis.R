library(stringr)
library(ggplot2)
library(ggpattern)
library(hrbrthemes)
library(dplyr)

theme <- theme(
  plot.margin = unit(c(1,1,1,1), "cm"),
  panel.grid.major.y = element_line(colour = "black", linetype = "dashed", size=0.1),
  # panel.grid.minor.y = element_line(colour = "black", linetype = "dashed"),
  panel.grid.minor.x = element_blank(),
  panel.grid.minor.y = element_blank(),
  plot.background = element_rect(
    fill="white"
  ),
  plot.title = element_text(size=18),
  axis.text.x = element_text(size = 12),
  axis.text.y = element_text(size = 12),
  axis.title.x=element_text(size=20),
  axis.title.y=element_text(size=20),
  legend.title = element_text(size = 18, face ="bold"),
  legend.text = element_text(size = 16),
  legend.background = element_rect(
    fill="white",
    size=0.2,
    linetype="solid",
    colour ="black"
  ),
  legend.text.align = 0,
  legend.position = c(.85,.88),
  legend.margin = margin(8, 18, 8, 8)
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

if (getwd() != "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/SpectrometerData/Data"){
  setwd("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/SpectrometerData/Data")
}


uniqueFiles <- c(
  "CFL62w.txt",
  "halogen50W.txt",
  "INCANDESENT40w.txt",
  "led4000k10w.txt"
)

CFL <- read.csv2(
  paste(getwd(),"/","CFL65w.txt",sep=""),
  sep="\t",
  header=FALSE,
  skip=16
)

CFL <- head(CFL, -1)
CFL$V1 <- as.numeric(CFL$V1)
CFL$V2 <- as.numeric(CFL$V2)


CFL2 <- read.csv2(
  paste(getwd(),"/","CFL62w.txt",sep=""),
  sep="\t",
  header=FALSE,
  skip=16
)

CFL2 <- head(CFL2, -1)
CFL2$V1 <- as.numeric(CFL2$V1)
CFL2$V2 <- as.numeric(CFL2$V2)

LED <- read.csv2(
  paste(getwd(),"/","led4000k10w.txt",sep=""),
  sep="\t",
  header=FALSE,
  skip=16
)
LED <- head(LED, -1)
LED$V1 <- as.numeric(LED$V1)
LED$V2 <- as.numeric(LED$V2)


LED6000 <- read.csv2(
  paste(getwd(),"/","led6500k10w.txt",sep=""),
  sep="\t",
  header=FALSE,
  skip=16
)
LED6000 <- head(LED6000, -1)
LED6000$V1 <- as.numeric(LED6000$V1)
LED6000$V2 <- as.numeric(LED6000$V2)

HALOGEN <- read.csv2(
  paste(getwd(),"/","halogen50W.txt",sep=""),
  sep="\t",
  header=FALSE,
  skip=16
)
HALOGEN <- head(HALOGEN, -1)
HALOGEN$V1 <- as.numeric(HALOGEN$V1)
HALOGEN$V2 <- as.numeric(HALOGEN$V2)

INCANDESENT <- read.csv2(
  paste(getwd(),"/","incondesent40w.txt",sep=""),
  sep="\t",
  header=FALSE,
  skip=16
)
INCANDESENT <- head(INCANDESENT, -1)
INCANDESENT$V1 <- as.numeric(INCANDESENT$V1)
INCANDESENT$V2 <- as.numeric(INCANDESENT$V2)

color_al <- 1
fill_al <- 0.2
ggplot() +
  geom_area(data=CFL,aes(x=V1,y=V2,fill="CFL"),alpha=fill_al)+
  geom_area(data=LED,aes(x=V1,y=V2,fill="LED"),alpha=fill_al)+
  geom_area(data=HALOGEN,aes(x=V1,y=V2,fill="Halogen"),alpha=fill_al)+
  geom_area(data=INCANDESENT,aes(x=V1,y=V2,fill="Incandesent"),alpha=fill_al)+
  geom_line(data=CFL,aes(x=V1,y=V2,color="CFL"),alpha=color_al)+
  geom_line(data=LED,aes(x=V1,y=V2,color="LED"),alpha=color_al)+
  geom_line(data=HALOGEN,aes(x=V1,y=V2,color="Halogen"),alpha=color_al)+
  geom_line(data=INCANDESENT,aes(x=V1,y=V2,color="Incandesent"),alpha=color_al)+
  ggtitle("Porównanie charakterystyk spektometrycznych źródeł światła") +
  theme_linedraw() +
  scale_x_continuous(name = "Wave length [nm]",
                     # limits = c(350, 900),
                     n.breaks = 12
  ) +
  scale_y_continuous(name = "Illuminance [lux]",
                     limits = c(0, 200),
                     n.breaks = 22
  )+
  theme+
  scale_color_manual(
    name='Źródło oświetlenia:',
    breaks=c('CFL', 'Halogen', 'Incandesent', 'LED'),
    values=c('CFL'='blue', 'Halogen'='orange', 'Incandesent'='red', 'LED'='green')
  )+
  scale_fill_manual(
    name='Źródło oświetlenia:',
    breaks=c('CFL', 'Halogen', 'Incandesent', 'LED'),
    values=c('CFL'='blue', 'Halogen'='orange', 'Incandesent'='red', 'LED'='green')
  )
ggsave(
  "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/Img/plot1.png",
  units="mm",
  width = 297,
  height = 210,
  dpi = 300
)



ggplot() +
  geom_area(data=LED,aes(x=V1,y=V2,fill="4000K"),alpha=fill_al)+
  geom_line(data=LED,aes(x=V1,y=V2,color="4000K"),alpha=color_al)+
  geom_area(data=LED6000,aes(x=V1,y=V2,fill="6500K"),alpha=fill_al)+
  geom_line(data=LED6000,aes(x=V1,y=V2,color="6500K"),alpha=color_al)+
  ggtitle("Porównanie charakterystyk spektometrycznych źródeł światła LED") +
  theme_linedraw() +
  scale_x_continuous(name = "Wave length [nm]",
                     # limits = c(350, 900),
                     n.breaks = 12
  ) +
  scale_y_continuous(name = "Illuminance [lux]",
                     limits = c(0, 200),
                     n.breaks = 22
  )+
  theme+
  scale_color_manual(
    name='Temperatura światła:',
    breaks=c('4000K', '6500K'),
    values=c('4000K'='orange','6500K'='blue')
  )+
  scale_fill_manual(
    name='Temperatura światła:',
    breaks=c('4000K', '6500K'),
    values=c('4000K'='orange','6500K'='blue')
  )
ggsave(
  "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/Img/plot3.png",
  units="mm",
  width = 297,
  height = 210,
  dpi = 300
)

ggplot() +
  geom_area(data=CFL,aes(x=V1,y=V2,fill="CFL65W"),alpha=fill_al)+
  geom_line(data=CFL,aes(x=V1,y=V2,color="CFL65W"),alpha=color_al)+
  geom_area(data=CFL2,aes(x=V1,y=V2,fill="CFL62W"),alpha=fill_al)+
  geom_line(data=CFL2,aes(x=V1,y=V2,color="CFL62W"),alpha=color_al)+
  ggtitle("Porównanie charakterystyk spektometrycznych źródeł światła CFL") +
  theme_linedraw() +
  scale_x_continuous(name = "Wave length [nm]",
                     # limits = c(350, 900),
                     n.breaks = 12
  ) +
  scale_y_continuous(name = "Illuminance [lux]",
                     limits = c(0, 200),
                     n.breaks = 22
  )+
  theme+
  scale_color_manual(
    name='Źródło oświetlenia:',
    breaks=c('CFL62W', 'CFL65W'),
    values=c('CFL62W'='orange','CFL65W'='blue')
  )+
  scale_fill_manual(
    name='Źródło oświetlenia:',
    breaks=c('CFL62W', 'CFL65W'),
    values=c('CFL62W'='orange','CFL65W'='blue')
  )
ggsave(
  "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/Img/plot4.png",
  units="mm",
  width = 297,
  height = 210,
  dpi = 300
)

# -----------------------------------------------------------------------------------------------------------------------

if (getwd() != "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/Data"){
  setwd("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/Data")
}

data <- read.csv("data.csv")

dataX <- data[7,]
primaryConcentration <- 5e-3 # mol/dm^3
intensityZero <- 167 # lx
calibrationData <- data[0:6,]

calibrationData$Intensity <- as.numeric(calibrationData$Intensity)
dataX$Intensity <- as.numeric(dataX$Intensity)
calibrationData$L.p <- as.numeric(calibrationData$L.p)

calibrationData$Concentration <- rev((1/(2^(calibrationData$L.p-1))) * primaryConcentration)
# calibrationData$Concentration <- 327.33*calibrationData$Concentration*1000

calibrationData$Absorbtion <- log10((intensityZero)/(calibrationData$Intensity))
dataX$Absorbtion <- log10((intensityZero)/(dataX$Intensity))


ggplot(data = calibrationData, aes(y=Absorbtion, x=Concentration))+
  ggtitle("Zmiana absorbancji względem stężenia oranżu metylowego") +
  geom_point(size=3)+
    scale_x_continuous(name = "Concentration [mol/dm^3]",
                       n.breaks = 12
    ) +
    scale_y_continuous(name = "Absorbance [j.u.]",
                       n.breaks = 12
    )+
  theme_linedraw()+
  theme+
  geom_smooth(method="lm",se=F,size=0.5,color="black")+
  geom_smooth(method="lm",data=calibrationData[1:4,],aes(y=Absorbtion, x=Concentration),se=F,fullrange=TRUE,size=0.5,col="black")+
  geom_hline(yintercept = dataX$Absorbtion, linetype="dashed",color="red")+
  geom_text(aes(y=0.044, x=0.0035, label=paste("Unknown concentration")), size = 6,color="red")+
  geom_label(aes(y=0.1, x=0.003, label=paste("4 points regression \n R^2=0.9775")), size = 6)+
  geom_label(aes(y=0.07, x=0.004, label=paste("6 points regression \n R^2=0.9757")), size = 6)
ggsave(
  "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab7/Img/plot2.png",
  units="mm",
  width = 297,
  height = 210,
  dpi = 300
)


reg <- lm(calibrationData$Absorbtion~(calibrationData$Concentration))
plot((calibrationData$Concentration), calibrationData$Absorbtion)
abline(reg)
abline(h=dataX$Absorbtion)
regVal <- regressionValues(reg, 4)
regVal$Slope*1000

xCon1 <- (dataX$Absorbtion-regVal$Intercept)/regVal$Slope
out <- calibrationData
calibrationData <- calibrationData[1:4,]
reg <- lm(calibrationData$Absorbtion~(calibrationData$Concentration))
regVal <- regressionValues(reg, 4)
regVal

# (dataX$Absorbtion-regVal$Intercept)/(regVal$Slope)

regVal

abline(reg)
# calibrationData

out$Concentration <- format(out$Concentration, scientific = TRUE)
out$Absorbtion <- round(out$Absorbtion, 5)
out <- out[,2:4]
out
write.csv(out, "outData.csv")