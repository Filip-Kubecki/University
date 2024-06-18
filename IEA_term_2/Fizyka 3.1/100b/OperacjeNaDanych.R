daneRezystor <- read.csv("F:\\Projekty Intellij\\Text\\100b\\Dane100bRezystor.csv")
daneBulb <- read.csv("F:\\Projekty Intellij\\Text\\100b\\Dane100bŻarówka.csv")

plot(daneRezystor$U.V.,daneRezystor$I..mA.)
abline(lm(daneRezystor$I..mA.~daneRezystor$U.V.))
plot(daneBulb$U.V.,daneBulb$I..mA.)

for (i in seq_along(daneBulb)){
  cat(daneBulb$U.V.[i],daneBulb$I..mA.[i])
  print((daneBulb$U.V.[i]/daneBulb$I..mA.[i])*1000)
}
linear <- lm(daneBulb$I..mA.~daneBulb$U.V.)
abline(linear)

coefficients <- summary(linear)$coefficients
print(coefficients)
print((1/coefficients[2,1])*1000)

# print(1/as.numeric(linear[1]))
# Save to file and end program
print("DONE -------------------------------------------------")