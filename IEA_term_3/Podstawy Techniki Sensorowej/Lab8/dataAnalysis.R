library(ggplot2)

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


if (getwd() != "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab8"){
  setwd("/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab8")
}
data <- read.csv("Data/data.csv")

data

pointSize <- 6
ggplot() +
  geom_point(data=data,aes(x=Distance,y=narrow),shape=1,size=pointSize)+
  # geom_smooth(data=data,aes(x=Distance,y=narrow),method="lm",se=FALSE)+
  geom_point(data=data,aes(x=Distance,y=medium),shape=2,size=pointSize)+
  # geom_smooth(data=data,aes(x=Distance,y=medium),method = "lm",se=FALSE)+
  geom_point(data=data,aes(x=Distance,y=wide),shape=13,size=pointSize)+
  # geom_smooth(data=data,aes(x=Distance,y=wide),method = "lm",se=FALSE)+
  ggtitle("Porównanie charakterysyk odpowiedzi przy zmianie trybu pracy") +
  theme_linedraw() +
  scale_x_continuous(name = "Distance [cm]",
                     # limits = c(350, 900),
                     n.breaks = 12
  ) +
  scale_y_continuous(name = "Spread [cm]",
                     # limits = c(0, 200),
                     n.breaks = 22
  )+
  theme
ggsave(
  "/home/bork/IdeaProjects/LatexProjects/src/PodstawyTechnikiSensorowej/Lab8/Img/plot1.png",
  units="mm",
  width = 297,
  height = 210,
  dpi = 300
)


