# Program do obliczania wyra¿enia nr 1
# Znaczenie u¿ytych rejestrów:

# $t0 – przechowuje b
# $t1 – przechwuje c
# $t2 – przechwuje d

# $t3 - przechowuje poœrednik wynik obliczeñ
# $t4 – tymczasowa do wyborów u¿ytkownika // ostateczny wynik obliczeñ
# $v0 – przechowuje stosowny parametr dla wywo³ania systemowego syscall oraz wczytan¹ 	#wartoœæ wejœciow¹
# $a0 – przechowuje parametr wywo³ania systemowego syscall

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
#Zapytanie o typ wyra¿enia
#Wypisz zapytanie o nnumer wyra¿enia
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, prompt     
	syscall      
#Wczytaj numer wyra¿enia 
	li $v0, 5         # 5 (kod dla syscall wczytaj liczbêv ca³kowit¹)
	syscall             
	move $t4, $v0   
#Gdy b³êdne dane
	bgt $t4, 3, errorinput
	blt $t4, 1, errorinput
	
#Wczytywanie wartoœci zmiennych b c d
#Wypisz zapytanie o zmienn¹
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, promptb     
	syscall     
# wczytaj pierwsz¹ liczbê, wstaw j¹ do $t0
	li $v0, 5         # 5 (kod dla syscall wczytaj liczbêv ca³kowit¹)
	syscall             
	move $t0, $v0 
#Wypisz zapytanie o zmienn¹
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, promptc     
	syscall     
# wczytaj drug¹ liczbê, wstaw j¹ do $t1
	li $v0, 5         # 5 (kod dla syscall wczytaj liczbê ca³kowit¹)    
	syscall     
	move $t1, $v0
#Wypisz zapytanie o zmienn¹
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, promptd     
	syscall   
# wczytaj trzeci¹ liczbê, wstaw j¹ do $t2
	li $v0, 5         #  5 (kod dla syscall wczytaj liczbê #ca³kowit¹)    
	syscall         
	move $t2, $v0 
	
#Reakcja na wybrane wyra¿enie
	beq $t4, 1, expression1
	beq $t4, 2, expression2
	beq $t4, 3, expression3
	j errorinput
	
expression1:
#Wyra¿enie nr 1
	subu $t3, $t1, $t2     # oblicz ró¿nice $t0 i $t1, wynik wpisz do $t3
	mulu $t4, $t3, $t0     # oblicz iloraz $t0 i $t1, wynik wpisz do $t2
	j output
expression2:
#Wyra¿enie nr 2
 	addu $t3, $t0, $t1     # oblicz ró¿nice $t0 i $t1, wynik wpisz do $t3
	subu $t4, $t3, $t2     # oblicz iloraz $t0 i $t1, wynik wpisz do $t2
	j output
	
expression3:
#Wyra¿enie nr 3
	beqz $t1 errorinputzero
	divu $t3, $t0, $t1     # oblicz ró¿nice $t0 i $t1, wynik wpisz do $t3
	mulu $t4, $t3, $t2     # oblicz iloraz $t0 i $t1, wynik wpisz do $t2
	j output
output:
# Wydrukuj wynik
	move $a0, $t4     # przenieœ drukowan¹ liczbê z $t4 do $a0
	li $v0, 1         # za³aduj do $v0 wartoœæ 1 (kod dla syscall drukuj liczbê     #ca³kowit¹)
	syscall             # wywo³aj syscall
#Wypisz prompt continue
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, continueprompt     
	syscall
#Wartoœæ dla prompta
	li      $v0, 5           # 4  (kod dla syscall wypisz string)
	syscall     
	move $t1, $v0
#Zale¿nie od wprowadzonych danych
	beq $t1, 1, main
	beq $t1, 0, end
	j errorinput

end:
	li $v0, 10         # za³aduj do $v0 wartoœæ 10 (kod dla syscall koniec programu)
	syscall             #wywo³aj syscall
# koniec 

errorinput:
#Wypisz prompt error
	#Wypisz prompt continue
	li      $v0, 4           # 4  (kod dla syscall wypisz string)
	la      $a0, errorprompt     
	syscall
#Wartoœæ dla prompta
	li      $v0, 5           # 4  (kod dla syscall wypisz string)
	syscall     
	move $t1, $v0
#Zale¿nie od wprowadzonych danych
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
#Wartoœæ dla prompta
	li      $v0, 5           # 4  (kod dla syscall wypisz string)
	syscall     
	move $t1, $v0
#Zale¿nie od wprowadzonych danych
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