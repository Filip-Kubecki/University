library(ggplot2)
dataset <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie7\\Dane\\Dane4.csv")
dataset <- dataset[-1,]

ggplot(dataset,aes(y=cos.phi., x=P)) +
  ggtitle("Zależność współczynnika mocy od obciążenia") +
  geom_point(fill="black", alpha=1) +
  theme_linedraw() +
  scale_y_continuous(name = "Power Factor",n.breaks = 5) +
  scale_x_continuous(name = "Load[W]",n.breaks = 10) +
  theme(plot.margin = unit(c(1,1,1,1), "cm"),
        panel.grid.major = element_line(colour = "black", linetype = "dashed"),
        panel.grid.minor = element_line(colour = "gray", linetype = "dashed"),
        axis.title.x=element_text(size=12),
        axis.title.y=element_text(size=12)
  )