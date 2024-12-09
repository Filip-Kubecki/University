library(ggplot2)


theme <- theme(
  plot.margin = unit(c(1,1,1,1), "cm"),
  panel.grid.major = element_line(colour = "black", linetype = "dashed"),
  panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
  axis.text.x = element_text(size = 12),
  axis.text.y = element_text(size = 12),
  axis.title.x=element_text(size=16),
  axis.title.y=element_text(size=16),
  legend.title = element_text(size = 10,face ="bold"),
  legend.background = element_rect(fill="white",
                                   size=0.2, linetype="solid",
                                   colour ="black"),
  legend.text.align = 1
)


lengthwise <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab3/Dane/lengthwise.csv")
lengthwiseR <- (lengthwise$R+lengthwise$R_rav)/2
lengthwise0 <- 34.1602
epsi_f_prim <- 1.5*((0.65*(32.2-1.1))/(32.2^3))
epsi_f_prim
epsi_f <- 0.9082332342
epsi_f
crosswise <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab3/Dane/crosswise.csv")
crosswiseR <- (crosswise$R+crosswise$R_rav)/2
crosswise0 <- 33.2442


# Podłużny
"Podluzny"
y <- (lengthwise$f*epsi_f)/1000
x <- (lengthwiseR-lengthwise0)/lengthwise0
regressionLen <- lm(x~y)
regressionLen$coefficients[2]
lenCoeff <- paste("GF - podłużny: ", round(regressionLen$coefficients[2],5))

"-----------------"
"Poprzeczny"
# Poprzeczny
y <- crosswise$f*epsi_f_prim
x <- crosswiseR/crosswise0
regressionCross <- lm(x~y)
regressionCross$coefficients[2]
crossCoeff <- paste("GF - poprzeczny: ", round(regressionCross$coefficients[2], 5))

# lengthwise$R/lengthwise0
# lengthwise$R_rav/lengthwise0

# crosswise$R/crosswise0
# crosswise$R_rav/crosswise0
# crosswise$R/crosswise0
# crosswise$R_rav/crosswise0
temp <- crosswise$R_rav/crosswise0
# max(temp)
# min(temp, na.rm = TRUE)
max(temp, na.rm = TRUE)-min(temp, na.rm = TRUE)
ggplot(data = lengthwise, aes(y=R/lengthwise0, x=f*epsi_f_prim)) +
  # geom_point(color = "darkturquoise") +
  # ggtitle("Charakterystyka dla efektu poprzecznego") +
  theme_linedraw() +
  scale_x_continuous(name = "ε",n.breaks = 20) +
  scale_y_continuous(name = "ΔR/R0",n.breaks = 16) +
  theme +
  # geom_point(data = lengthwise, aes(y=R_rav/lengthwise0, x=f*epsi_f_prim), color = "red") +
  # geom_point(data = crosswise, aes(y=R/crosswise0, x=f*epsi_f_prim), color = "red") +
  # geom_point(data = crosswise, aes(y=R_rav/crosswise0, x=f*epsi_f_prim), color = "red") +
  geom_smooth(data = lengthwise, aes(y=(R-lengthwise0)/lengthwise0, x=f*epsi_f_prim), method = "lm", color = "red", size=0.6, se=F)+
  geom_smooth(data = crosswise, aes(y=(R-crosswise0)/crosswise0, x=f*epsi_f_prim), method = "lm", color = "blue", size=0.6, se=F)+
  geom_label(
    label=lenCoeff,
    x=3e-04,
    y=0.005,
    label.padding = unit(0.55, "lines"),
    label.size = 0.2,
    color = "black",
    size = 6
  ) +
  geom_label(
    label=crossCoeff,
    x=5.5e-04,
    y=0.001,
    label.padding = unit(0.55, "lines"),
    label.size = 0.2,
    color = "black",
    size = 6
  )

oData <- lengthwise$f
oData <- cbind(oData, lengthwise$f*epsi_f_prim)
oData <- cbind(oData, (lengthwise$R-lengthwise0)/lengthwise0)

oData

