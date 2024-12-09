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

data <- read.csv("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab2/Data/data.csv")
uAtmPt100 <- 2.97
uAtmPt101 <- 44.9*10^(-3)

# Pressure for Pt100
pBarPt100 <- (1.15 - 0.95)/(10-0)*data$U_pt100+0.95
pPaPt100 <- pBarPt100*100000
data <- cbind(data, pBarPt100)
data <- cbind(data, pPaPt100)

# Pressure for Pt101
pBarPt101 <- (0.1)/(10-0)*data$U_pt101
pPaPt101 <- pBarPt101*100000
data <- cbind(data, pBarPt101)
data <- cbind(data, pPaPt101)

# Height of liquid column
h <- (data$V..ml./100)*6.28760269005018
data <- cbind(data, h)

# Plot and linear model for Pt100
regression <- lm(data$pPaPt100~data$h)
"a"
coeff <- regression$coefficients[2][1]
rho <- (regression$coefficients[2][1]/9.81)*100
"rho"
rho

ggplot(data = data, aes(y=pPaPt100, x=h)) +
  geom_smooth(method = lm, color = "black", size=0.4) +
  geom_point(color = "red", size = 2) +
  ggtitle("Charakterystyka pt100") +
  theme_linedraw() +
  scale_x_continuous(name = "h [cm]",n.breaks = 20) +
  scale_y_continuous(name = "p [Pa]",n.breaks = 16) +
  theme +
  geom_label(
    label=paste("a = ",as.character(round(coeff, 4)), "..."),
    x=20,
    y=108000,
    label.padding = unit(0.55, "lines"),
    label.size = 0.2,
    color = "black",
    size = 8
  )
ggsave('/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab2/Img/pt100.png', width = 16, height = 9, dpi = 125)


# Plot and linear model for Pt101
regression <- lm(data$pPaPt101~data$h)
"a"
coeff <- regression$coefficients[2][1]
rho <- ((regression$coefficients[2][1])/9.81)*100
"rho"
rho
ggplot(data = data, aes(y=pPaPt101, x=h)) +
  geom_smooth(method = lm, color = "black", size=0.4) +
  geom_point(color = "red", size = 2) +
  ggtitle("Charakterystyka pt101") +
  theme_linedraw() +
  scale_x_continuous(name = "h [cm]",n.breaks = 20) +
  scale_y_continuous(name = "p [Pa]",n.breaks = 16) +
  theme +
  geom_label(
    label=paste("a = ",as.character(round(coeff, 4)), "..."),
    x=20,
    y=7000,
    label.padding = unit(0.55, "lines"),
    label.size = 0.2,
    color = "black",
    size = 8
  )
ggsave('/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab2/Img/pt101.png', width = 16, height = 9, dpi = 125)



odata <- data$V..ml.
odata <- cbind(odata, round(data$h, 4))
# odata <- cbind(odata, data$U_pt100)
odata <- cbind(odata, round(data$pBarPt100, 6))
odata <- cbind(odata, round(data$pPaPt100, 6))
# odata <- cbind(odata, data$U_pt101)
odata <- cbind(odata, round(data$pBarPt101, 6))
odata <- cbind(odata, round(data$pPaPt101, 6))

write.csv(odata,"/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab2/Data/oData.csv", row.names =FALSE)