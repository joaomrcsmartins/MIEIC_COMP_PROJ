# COMP - Project 1

For this project, you need to [install Gradle](https://gradle.org/install/)

## Project setup

Copy your ``.jjt`` file to the ``javacc`` folder. If you change any of the classes generated by ``jjtree`` or ``javacc``, you also need to copy them to the ``javacc`` folder.

Copy your source files to the ``src`` folder, and your JUnit test files to the ``test`` folder.

## Compile

To compile the program, run ``gradle build``. This will compile your classes to ``classes/main/java`` and copy the JAR file to the root directory. The JAR file will have the same name as the repository folder.

### Run

To run you have two options: Run the ``.class`` files or run the JAR.

### Run ``.class``

To run the ``.class`` files, do the following:

```cmd
java -cp "./build/classes/java/main/" <class_name> <arguments>
```

Where ``<class_name>`` is the name of the class you want to run and ``<arguments>`` are the arguments to be passed to ``main()``.

### Run ``.jar``

To run the JAR, do the following command:

```cmd
java -jar <jar filename> [-d] <arguments>
```

Where ``<jar filename>`` is the name of the JAR file that has been copied to the root folder, `` -d `` is the debug mode flag, and ``<arguments>`` are the arguments to be passed to ``main()``.

## Test

To test the program, run ``gradle test``. This will execute the build, and run the JUnit tests in the ``test`` folder. If you want to see output printed during the tests, use the flag ``-i`` (i.e., ``gradle test -i``).

## Checkpoint 2
### Checklist

#### Semantic Analysis
- [x] Todas as verificações feitas na análise semantica devem reportar erro excepto a verificação de inicialização de variáveis que deverá apenas dar um warning.

##### Symbol Table
    
   - [x] global: inclui info de imports e a classe declarada
   - [x] classe-specific: inclui info de extends, fields e methods
   - [x] method-specific: inclui info dos arguments e local variables
   - [x] sub topics:
       - [x] tem de permitir method overload (i.e. métodos com mesmo nome mas assinatura de parâmetros diferente)
       - [x] tem de permitir consulta da tabela por parte da análise semantica (e geração de código)
       - [x] tem de permitir ligar e desligar a sua impressão para fins de debug (neste caso para fins de avaliação)
- [ ] Type Verification
    - [x] verificar se operações são efetuadas com o mesmo tipo (e.g. int + boolean tem de dar erro)
    - [x] não é possível utilizar arrays diretamente para operações aritmeticas (e.g. array1 + array2)
    - [x] verificar se um array access é de facto feito sobre um array
    - [x] verificar se o indice do array access é um inteiro
    - [x] verificar se valor do assignee é igual ao do assigned (a_int = b_boolean não é permitido!)
    - [x] verificar se operação booleana é efetuada só com booleanos
    - [x] verificar se conditional expressions (if e while) resulta num booleano
    - [x] verificar se variáveis são inicializadas, dando um WARNING em vez de ERRO
       - [x] parametros são assumidos como inicializados
       - [x] devem fazer uma análise através do control flow, i.e., se há um if e a variável só é inicializada dentro de ou o then ou o else, deve-se dar um warning a indicar que poderá não estar inicializada
       - [ ] será considerado bónus a quem resolver esta verificação usando erros em vez de warning.
            - Poderia ter sido utilizado esta forma, mas alguns testes falham à custa disso, por isso não foi colocado.
			
- [x] Function Verification
	- [x] verificar se o "target" do método existe, e se este contém o método (e.g. a.foo, ver se 'a' existe e se tem um método 'foo')
	    - [x] caso seja do tipo da classe declarada (e.g. a usar o this), verificar se é método do extends olhando para o que foi importado (isto se a classe fizer extends de outra classe importada)
	- [x] caso o método não seja da classe declarada, isto é importada, verificar se método foi importado
	- [x] verificar se o número de argumentos na invocação é igual ao número de parâmetros da declaração
	- [x] verificar se o tipo dos parâmetros coincide com o tipo dos argumentos
	    - [x] não esquecer que existe method overloading-= Code Generation =-
    - [x] estrutura básica de classe (incluindo construtor <init>)
	- [x] estrutura básica de fields
	- [x] estrutura básica de métodos (podem desconsiderar os limites neste checkpoint: limit_stack 99, limit_locals 99)
	- [x] assignments
	- [x] operações aritméticas (com prioridade de operações correta)
		- neste checkpoint não é necessário a seleção das operações mais eficientes mas isto será considerado no CP3 e versão final
	- [x] invocação de métodos