dataset <- read.csv("F:\\Projekty Intellij\\Text\\Metrologia\\Ćwiczenie3\\Dane\\Czesc16.csv")



plot(dataset,
     log='x',
     ylab="Voltage [mV]",
     xlab="Frequency [Hz]",
     type="b",
     pch=20,
     xlim=c(10,10000000),
     ylim=c(0,1050)
     )
grid(nx = NULL, ny = NULL,
     lty = 2,      # Grid line type
     col = "gray", # Grid line color
     lwd = 2)      # Grid line width

abline(707,0, col="red", lwd=1.5)