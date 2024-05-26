library(ggplot2)
library(ggpubr)

brightnessSensor <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\Brightness.csv")
distanceReflective5 <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\DistanceReflective5V.csv")
distanceReflective4_5 <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\DistanceReflective4_5V.csv")
distanceReflective4 <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\DistanceReflective4V.csv")

distanceDull5 <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\DistanceDull5V.csv")
distanceDull4_5 <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\DistanceDull4_5V.csv")
distanceDull4 <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\DistanceDull4V.csv")

noise <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie8\\Dane\\Noise.csv")

distanceTheme <- theme(plot.margin = unit(c(1,1,1,1), "cm"),
                       panel.grid.major = element_line(colour = "black", linetype = "dashed"),
                       panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
                       axis.title.x=element_text(size=12),
                       axis.title.y=element_text(size=12))

distanceThemeH <- theme(
   plot.margin = unit(c(1,1,1,1), "cm"),
   panel.grid.major = element_line(colour = "black", linetype = "dashed"),
   panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
   axis.title.x=element_text(size=12),
   axis.title.y=element_text(size=12),
   legend.position = c(0.1, 0.86),
   legend.title = element_text(size = 10,face ="bold"),
   legend.background = element_rect(fill="white",
                                    size=0.2, linetype="solid",
                                    colour ="black"),
   legend.text.align = 1
  )


# Brightness sensor Graph
# reg5 <- lm(data = brightnessSensor, U5~Pr)
# plot(y = brightnessSensor$U5,x = brightnessSensor$Pr)
# abline(reg5)
# summary(reg5)


# ggplot(brightnessSensor,aes(x=Pr)) +
#   ggtitle("Zależność napięcia od jasności powierzchni") +
#   theme_linedraw() +
#   scale_color_discrete(name="Vcc:") +
#   scale_y_continuous(name = "Voltage[V]",n.breaks = 10) +
#   scale_x_continuous(name = "Brightness[%]",n.breaks = 10) +
#   theme(plot.margin = unit(c(1,1,1,1), "cm"),
#         panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#         panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#         axis.title.x=element_text(size=12),
#         axis.title.y=element_text(size=12),
#         legend.position = c(0.9, 0.83),
#         legend.title = element_text(size = 12,face ="bold"),
#         legend.background = element_rect(fill="white",
#                                          size=0.2, linetype="solid",
#                                          colour ="black"),
#         legend.text.align = 1
#   ) +
#   geom_point(aes(y = U5, colour = '5 [V]')) +
#   geom_point(aes(y = U4_5, colour = '4.5 [V]')) +
#   geom_point(aes(y = U4, colour = '4 [V]'))

# Distance sensor reflective surface Vcc = 5[V] full range
dr1 <- ggplot(distanceReflective5,aes(y=Vr*1000, x=L)) +
  ggtitle("Napięcie zasilania 5 [V] - powierzchnia błyszcząca") +
  theme_linedraw() +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 20) +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 20) +
  distanceTheme +
  geom_point(colour="firebrick3")

# Distance sensor reflective surface Vcc = 5[V] hysteresis loop
dr2 <- ggplot(subset(distanceReflective5,L>2 & L<17),aes(x=L)) +
  ggtitle("Napięcie zasilania 5[V] - powierzchnia błyszcząca") +
  scale_color_discrete(name="Typ:") +
  theme_linedraw() +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
  distanceThemeH +
  geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
  geom_point(aes(y = Vd*1000, colour = "Malejąca"))

# Distance sensor reflective surface Vcc = 4.5[V] hysteresis loop
dr3 <- ggplot(subset(distanceReflective4_5,L>2 & L<17),aes(y=Vr, x=L)) +
  ggtitle("Napięcie zasilania 4.5[V] - powierzchnia błyszcząca") +
  scale_color_discrete(name="Typ:") +
  theme_linedraw() +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
  distanceThemeH +
  geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
  geom_point(aes(y = Vd*1000, colour = "Malejąca"))

# Distance sensor reflective surface Vcc = 4[V] hysteresis loop
dr4 <- ggplot(subset(distanceReflective4,L>2 & L<17),aes(y=Vr, x=L)) +
  ggtitle("Napięcie zasilania 4[V] - powierzchnia błyszcząca") +
  scale_color_discrete(name="Typ:") +
  theme_linedraw() +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
  distanceThemeH +
  geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
  geom_point(aes(y = Vd*1000, colour = "Malejąca"))

# Distance sensor dull surface Vcc = 5[V] full
dd1 <- ggplot(distanceDull5,aes(x=L)) +
  ggtitle("Napięcie zasilania 5[V] - powierzchnia matowa") +
  scale_color_discrete(name="Typ:") +
  theme_linedraw() +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
  distanceThemeH +
  geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
  geom_point(aes(y = Vd*1000, colour = "Malejąca"))

# Distance sensor dull surface Vcc = 5[V] hysteresis loop
dd2 <- ggplot(subset(subset(distanceDull5,L>2 & L<17),L>2 & L<17),aes(x=L)) +
  ggtitle("Napięcie zasilania 5[V] - powierzchnia matowa") +
  scale_color_discrete(name="Typ:") +
  theme_linedraw() +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
  distanceThemeH +
  geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
  geom_point(aes(y = Vd*1000, colour = "Malejąca"))

# Distance sensor dull surface Vcc = 4.5[V] hysteresis loop
dd3 <- ggplot(subset(subset(distanceDull4_5,L>2 & L<17),L>2 & L<17),aes(x=L)) +
  ggtitle("Napięcie zasilania 4.5[V] - powierzchnia matowa") +
  scale_color_discrete(name="Typ:") +
  theme_linedraw() +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
  distanceThemeH +
  geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
  geom_point(aes(y = Vd*1000, colour = "Malejąca"))

# Distance sensor dull surface Vcc = 4[V] hysteresis loop
dd4 <- ggplot(subset(subset(distanceDull4,L>2 & L<17),L>2 & L<17),aes(x=L)) +
  ggtitle("Napięcie zasilania 4[V] - powierzchnia matowa") +
  scale_color_discrete(name="Typ:") +
  theme_linedraw() +
  scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
  scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
  distanceThemeH +
  geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
  geom_point(aes(y = Vd*1000, colour = "Malejąca"))

# # Noise
# ggplot(noise,aes(x=L)) +
#   # ggtitle("Zależność napięcia od jasności powierzchni") +
#   theme_linedraw() +
#   scale_color_discrete(name="Typ próbki:") +
#   scale_y_continuous(name = "Voltage[mV]",n.breaks = 10) +
#   scale_x_continuous(name = "Distance[mm]",n.breaks = 10) +
#   theme(plot.margin = unit(c(1,1,1,1), "cm"),
#         panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#         panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#         axis.title.x=element_text(size=12),
#         axis.title.y=element_text(size=12),
#         legend.position = c(0.15, 0.8),
#         legend.title = element_text(size = 12,face ="bold"),
#         legend.background = element_rect(fill="white",
#                                          size=0.2, linetype="solid",
#                                          colour ="black"),
#         legend.text.align = 0
#   ) +
#   geom_point(aes(y = Vd*1000, colour = 'Próba właściwa')) +
#   geom_point(aes(y = S6*1000, colour = 'Czułość P6')) +
#   geom_point(aes(y = S10*1000, colour = 'Czułość P10')) +
#   geom_point(aes(y = S14*1000, colour = 'Czułość P14'))

# # Distance sensor reflective surface Vcc = 4.5[V] hysteresis loop - regresion
# ggplot(subset(distanceReflective4_5,L>2 & L<17),aes(y=Vr, x=L)) +
#   ggtitle("Napięcie zasilania 4.5[V] - powierzchnia błyszcząca - regresja") +
#   scale_color_discrete(name="Typ:") +
#   theme_linedraw() +
#   scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
#   scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
#   distanceThemeH +
#   geom_point(aes(y = Vr*1000, colour = "Rosnąca")) +
#   geom_point(aes(y = Vd*1000, colour = "Malejąca")) +
#   geom_smooth(aes(y = 1000*((Vr+Vd)/2),x = L),se=F,data = subset(distanceReflective4_5,L>2 & L<17),method ='lm',colour = "red",linewidth = 0.2)
#
# reg7 <- lm(data = subset(distanceReflective4_5,L>2 & L<17), (1000*((Vr+Vd)/2))~L)
# summary(reg7)

# # Distance sensor reflective surface Vcc = 4.5[V] hysteresis loop - regresion
# ggplot(subset(distanceReflective4_5,L>2 & L<17),aes(x=L)) +
#   # ggtitle("Napięcie zasilania 4.5[V] - powierzchnia błyszcząca - regresja") +
#   scale_color_discrete(name="Typ:") +
#   theme_linedraw() +
#   scale_y_continuous(name = "Voltage [mV]",n.breaks = 10) +
#   scale_x_continuous(name = "Distance [mm]",n.breaks = 8) +
#   theme(
#     plot.margin = unit(c(1,1,1,1), "cm"),
#     panel.grid.major = element_line(colour = "black", linetype = "dashed"),
#     panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
#     axis.title.x=element_text(size=12),
#     axis.title.y=element_text(size=12),
#     legend.position = c(0.1, 0.7),
#     legend.title = element_text(size = 10,face ="bold"),
#     legend.background = element_rect(fill="white",
#                                      size=0.2, linetype="solid",
#                                      colour ="black"),
#     legend.text.align = 1
#   ) +
#   geom_point(aes(y = (Vd+Vr)/2*1000, colour = "5V ref"),data = subset(distanceReflective5,L>2 & L<17)) +
#   geom_point(aes(y = (Vd+Vr)/2*1000, colour = "4.5V ref"),data = subset(distanceReflective4_5,L>2 & L<17)) +
#   geom_point(aes(y = (Vd+Vr)/2*1000, colour = "4.5V dull"),data = subset(distanceDull4_5,L>2 & L<17)) +
#   geom_point(aes(y = ((Vd+Vr)/2*1000), colour = "5V dull"),data = subset(distanceDull5,L>2 & L<17)) +
#   geom_point(aes(y = ((Vd+Vr)/2*1000), colour = "5V dick"),data = subset(distanceDull5,L>2 & L<17)) +
#   geom_point(aes(y = (Vd+Vr)/2*1000, colour = "4V dull"),data = subset(distanceDull4,L>2 & L<17)) +
#   geom_point(aes(y = (Vd+Vr)/2*1000, colour = "4V ref"),data = subset(distanceReflective4,L>2 & L<17))

ggarrange(dr1, dr2, dr3, dr4,
          labels = c("A", "B", "C","D"),
          ncol = 2, nrow = 2)