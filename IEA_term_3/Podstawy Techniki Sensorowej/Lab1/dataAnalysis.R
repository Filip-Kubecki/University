library(ggplot2)

coolingData <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab1/Dane/cooling.csv")
referenceData <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab1/Dane/reference.csv")

# GGPLOT themes
theme1 <- theme(plot.margin = unit(c(1,1,1,1), "cm"),
                       panel.grid.major = element_line(colour = "black", linetype = "dashed"),
                       panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
                       axis.title.x=element_text(size=12),
                       axis.title.y=element_text(size=12))

theme2 <- theme(
  plot.margin = unit(c(1,1,1,1), "cm"),
  panel.grid.major = element_line(colour = "black", linetype = "dashed"),
  panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
  axis.text.x = element_text(size = 12),
  axis.text.y = element_text(size = 12),
  axis.title.x=element_text(size=16),
  axis.title.y=element_text(size=16),
  legend.position = c(0.1, 0.86),
  legend.title = element_text(size = 10,face ="bold"),
  legend.background = element_rect(fill="white",
                                   size=0.2, linetype="solid",
                                   colour ="black"),
  legend.text.align = 1
)

# PT100 analysis -------------------------------------------------------------------------------------------------------------
coldRefrenceTempPt100 <- referenceData$Rpt100.Ohm.[2]
hotRefrenceTempPt100 <- referenceData$Rpt100.Ohm.[1]

TWRpt100 <- ((referenceData$Rpt100.Ohm.[1]-referenceData$Rpt100.Ohm.[2]))/(referenceData$Rpt100.Ohm.[2]*(100))
coldRefrenceTempPt100

tempPt100 <- (((coolingData$Rpt100.Ohm.-coldRefrenceTempPt100))/(coldRefrenceTempPt100*TWRpt100))

tempPt100 <- (((coolingData$Rpt100.Ohm.)/(coldRefrenceTempPt100)-1)/TWRpt100)

coolingData$TempPt100.C. <- tempPt100

write.csv(rev(coolingData), "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab1/Dane/modifiedCoolingData2.csv")

# plot(tempPt100, coolingData$Rpt100.Ohm.)
TWRpt100
regression <- lm(coolingData$Rpt100.Ohm.~tempPt100)
regression$coefficients
ggplot(data = coolingData,aes(y=Rpt100.Ohm., x=tempPt100)) +
  geom_point(size = 3, color = "firebrick3") +
  ggtitle("Termorezystor PT100 - rezystancja od temperatury") +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature [°C]",n.breaks = 20) +
  scale_y_continuous(name = "Resistance [Ω]",n.breaks = 16) +
  theme2 +
  geom_point(colour="firebrick3") +
  geom_label(
    label="Coefficient: 3842.3 [ppm/K]",
    x=70,
    y=134,
    label.padding = unit(0.55, "lines"),
    label.size = 0.4,
    color = "black",
    size = 6
  ) +
  geom_smooth(method="lm", size = 0.4)

# abline(regression)

# TWRpt100
# regression$coefficients

directionCoeff <- regression$coefficients[2]
interception <- regression$coefficients[1]
# directionCoeff
# tempPt100

# plot(coolingData$Rpt100.Ohm., tempPt100, xlim=c(0,140),ylim=c(0,100))
# regression <- lm(tempPt100~coolingData$Rpt100.Ohm.)
# abline(regression)
# regression$coefficients


# Thermistor ------------------------------------------------------------------------------------------
# plot(tempPt100, coolingData$Rtherm.kOhm.)
ggplot(mapping = aes(y=coolingData$Rtherm.kOhm., x=tempPt100)) +
  geom_point(size = 3, color = "firebrick3") +
  ggtitle("Termistor - rezystancja od temperatury") +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature [°C]",n.breaks = 20) +
  scale_y_continuous(name = "Resistance [kΩ]",n.breaks = 16) +
  theme2 +
  geom_point(colour="firebrick3")
  # geom_label(
  #   label="Coefficient: 3842.3 [ppm/K]",
  #   x=70,
  #   y=134,
  #   label.padding = unit(0.55, "lines"),
  #   label.size = 0.4,
  #   color = "black",
  #   size = 6
  # ) +

coldRefrenceTempTherm <- referenceData$Rtherm.kOhm.[2]
hotRefrenceTempTherm <- referenceData$Rtherm.kOhm.[1]

lnRtR0 <- log((coolingData$Rtherm.kOhm.*1000)/(coldRefrenceTempTherm))
fnTT0 <- ((1/(tempPt100+273.15))-(1/273.15))-273.15

print("reg-------------------------------------")

# plot(fnTT0, lnRtR0)
regression <- lm(lnRtR0~fnTT0)
regression$coefficients

print("reg-------------------------------------")
ggplot(mapping = aes(y=lnRtR0, x=fnTT0)) +
  geom_point(size = 3, color = "firebrick3") +
  ggtitle("Termistor - charakterystyka do współczynnika B") +
  theme_linedraw() +
  scale_x_continuous(name = "fn(1/T - 1/T_0)",n.breaks = 4) +
  scale_y_continuous(name = "ln(R_T/R_0)",n.breaks = 16) +
  theme2 +
  geom_point(colour="firebrick3") +
  geom_label(
    label="Coefficient: 3842.3 [ppm/K]",
    x=-273.1508,
    y=3.85,
    label.padding = unit(0.55, "lines"),
    label.size = 0.4,
    color = "black",
    size = 6
  ) +
  geom_smooth(method="lm", size = 0.4)
# abline(regression)
# regression$coefficients
# NTC B4000/4050 10k

# Thermalcouple -----------------------------------------------------------------------------------------
# plot(tempPt100, coolingData$epsil.mV.)
regression <- lm(coolingData$epsil.mV.~tempPt100)
regression$coefficients

ggplot(mapping = aes(y=coolingData$epsil.mV., x=tempPt100)) +
  geom_point(size = 3, color = "firebrick3") +
  ggtitle("Termopara - charakterystyka napięcia od temperatury") +
  theme_linedraw() +
  scale_x_continuous(name = "Temperature [°C]",n.breaks = 18) +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 12) +
  theme2 +
  geom_point(colour="firebrick3") +
  geom_label(
    label="Coefficie",
    x=80,
    y=2,
    label.padding = unit(0.55, "lines"),
    label.size = 0.4,
    color = "black",
    size = 4
  ) +
  geom_smooth(method="lm", size = 0.4)
# abline(regression)
# regression$coefficients

# ggplot(mapping = aes(y=coolingData$epsil.mV., x=tempPt100-25)) +
#   geom_point(size = 3, color = "firebrick3") +
#   ggtitle("Termopara - charakterystyka napięcia od temperatury") +
#   theme_linedraw() +
#   scale_x_continuous(name = "T-T_0 [°C]",n.breaks = 18) +
#   scale_y_continuous(name = "ε [mV]",n.breaks = 12) +
#   theme2 +
# geom_point(colour="firebrick3") +
# geom_label(
#   label="Coefficient: 3842.3 [ppm/K]",
#   x=-273.1508,
#   y=3.85,
#   label.padding = unit(0.55, "lines"),
#   label.size = 0.4,
#   color = "black",
#   size = 6
# ) +
# geom_smooth(method="lm", size = 0.4)

# Termopara typu k
