values <- c()
uncertaintyAnalog <- c()
library("calculus")

accuracyClass <- 5
range <- 10
sampleRate <- 1500

absoluteUncertainty <- (accuracyClass*range)/100

for (i in 1:sampleRate){
  values <- append(values, (((range-1)/sampleRate)*i)+1)
}
print(values[1])

for (i in 1:sampleRate){
  uncertaintyAnalog <- append(uncertaintyAnalog,absoluteUncertainty/values[i])
}

plot(values,uncertaintyAnalog,
     main = "Zależność niepewności względnej od napięcia",
     xlab="Napięcie[V]",
     ylab="Niepewnosć względna[%]",
     pch=20
     )

# -----------------------------------------

values <- c()
uncertaintyDigital <- c()
uncertaintyDeriva <- c()

accuracy <- 0.00002
c <- 0.000006
range <- 100



for (i in 1:sampleRate){
  values <- append(values, (((range-1)/sampleRate)*i)+1)
}

for (i in 1:sampleRate){
  uncertaintyDigital <- append(uncertaintyDigital,((accuracy*i+c*range)/values[i]))
}

for (i in 1:sampleRate){
  uncertaintyDeriva <- append(uncertaintyDeriva,derivative("a*X+c*Xm",c(a = accuracy,)))
}

print(uncertaintyDigital[sampleRate-1])
print(uncertaintyDigital[1])

plot(values,as.double(uncertaintyDigital),
     main = "Zależność niepewności względnej od napięcia",
     xlab="Napięcie[V]",
     ylab="Niepewnosć względna[%]",
     pch=20
)

plot(values,uncertaintyDeriva,
     main = "Zależność niepewności względnej od napięcia",
     xlab="Napięcie[V]",
     ylab="Niepewnosć względna[%]",
     pch=20
)