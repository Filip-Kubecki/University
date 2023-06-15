# Program do obliczania wyrażenia nr 1
# Znaczenie użytych rejestrów:

# $t0 – przechowuje b
# $t1 – przechwuje c
# $t2 – przechwuje d

# $t3 - przechowuje pośrednik wynik obliczeń
# $t4 – tymczasowa do wyborów użytkownika // ostateczny wynik obliczeń
# $v0 – przechowuje stosowny parametr dla wywołania systemowego syscall oraz wczytaną 	#wartość wejściową
# $a0 – przechowuje parametr wywołania systemowego syscall

.data
prompt:			.asciiz		"Numer wyrazenia, ktorego wartosc nalezy obliczyc:\n 1. b*(c-d) \n 2. (b+c)-d \n 3. b/c*d \n Numer wyrazenia:"
promptb:		.asciiz		"Podaj zmienna b:"
promptc:		.asciiz		"Podaj zmienna c:"
promptd:		.asciiz		"Podaj zmienna d:"
errorprompt:		.asciiz		"Bledne dane. Czy kontynuowac? \n 0. NIE \n 1. TAK \n"
errorpromptzero:		.asciiz		"Bledne dane.Dzielenie przez zero. Czy kontynuowac? \n 0. NIE \n 1. TAK \n"
continueprompt:		.asciiz		"\nCzy kontynuowac? \n 0. NIE \n 1. TAK \n"
overfloweprompt:	.asciiz		"Overflow. Czy kontynuowac? \n 0. NIE \n 1. TAK \n"

.text
main:
#Zapytanie o typ wyrażenia
#Wypisz zapytanie o nnumer wyrażenia
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, prompt     
	syscall      
#Wczytaj numer wyrażenia 
	li $v0, 5         # 5 (kod dla syscall wczytaj liczbęv całkowitą)
	syscall             
	move $t4, $v0   
#Gdy błędne dane
	bgt $t4, 3, errorinput
	blt $t4, 1, errorinput
	
#Wczytywanie wartości zmiennych b c d
#Wypisz zapytanie o zmienną
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, promptb     
	syscall     
# wczytaj pierwszą liczbę, wstaw ją do $t0
	li $v0, 5         # 5 (kod dla syscall wczytaj liczbęv całkowitą)
	syscall             
	move $t0, $v0 
#Wypisz zapytanie o zmienną
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, promptc     
	syscall     
# wczytaj drugą liczbę, wstaw ją do $t1
	li $v0, 5         # 5 (kod dla syscall wczytaj liczbę całkowitą)    
	syscall     
	move $t1, $v0
#Wypisz zapytanie o zmienną
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, promptd     
	syscall   
# wczytaj trzecią liczbę, wstaw ją do $t2
	li $v0, 5         #  5 (kod dla syscall wczytaj liczbę #całkowitą)    
	syscall         
	move $t2, $v0 
	
#Reakcja na wybrane wyrażenie
	beq $t4, 1, expression1
	beq $t4, 2, expression2
	beq $t4, 3, expression3
	j errorinput
	
expression1:
#Wyrażenie nr 1
	subu $t3, $t1, $t2     # oblicz różnice $t0 i $t1, wynik wpisz do $t3
	mulu $t4, $t3, $t0     # oblicz iloraz $t0 i $t1, wynik wpisz do $t2
	j output
expression2:
#Wyrażenie nr 2
 	addu $t3, $t0, $t1     # oblicz różnice $t0 i $t1, wynik wpisz do $t3
	subu $t4, $t3, $t2     # oblicz iloraz $t0 i $t1, wynik wpisz do $t2
	j output
	
expression3:
#Wyrażenie nr 3
	beqz $t1 errorinputzero
	divu $t3, $t0, $t1     # oblicz różnice $t0 i $t1, wynik wpisz do $t3
	mulu $t4, $t3, $t2     # oblicz iloraz $t0 i $t1, wynik wpisz do $t2
	j output
output:
# Wydrukuj wynik
	move $a0, $t4     # przenieś drukowaną liczbę z $t4 do $a0
	li $v0, 1         # załaduj do $v0 wartość 1 (kod dla syscall drukuj liczbę     #całkowitą)
	syscall             # wywołaj syscall
#Wypisz prompt continue
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, continueprompt     
	syscall
#Wartość dla prompta
	li      $v0, 5           # 4  (kod dla syscall wypisz string)
	syscall     
	move $t1, $v0
#Zależnie od wprowadzonych danych
	beq $t1, 1, main
	beq $t1, 0, end
	j errorinput

end:
	li $v0, 10         # załaduj do $v0 wartość 10 (kod dla syscall koniec programu)
	syscall             #wywołaj syscall
# koniec 

errorinput:
#Wypisz prompt error
	#Wypisz prompt continue
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, errorprompt     
	syscall
#Wartość dla prompta
	li      $v0, 5           # 4  (kod dla syscall wypisz string)
	syscall     
	move $t1, $v0
#Zależnie od wprowadzonych danych
	beq $t1, 1, main
	beq $t1, 0, end
	j errorinput
# koniec 

errorinputzero:
#Wypisz prompt error
	#Wypisz prompt continue
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, errorpromptzero     
	syscall
#Wartość dla prompta
	li      $v0, 5           # 4  (kod dla syscall wypisz string)
	syscall     
	move $t1, $v0
#Zależnie od wprowadzonych danych
	beq $t1, 1, main
	beq $t1, 0, end
	j errorinput
# koniec

overflow:
#Wypisz prompt error
	#Wypisz prompt continue
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, overfloweprompt     
	syscall
	j end
