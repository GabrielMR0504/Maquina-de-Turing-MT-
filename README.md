Maquina de Turing (MT)
Autor: Gabriel de Medeiros Ribeiro

O programa irá receber uma especificação de Maquina de Turing (MT) e uma palavra de entrada e verificar se essa palavra pertence ou não a linguagem descrita por essa maquina.

![image](https://user-images.githubusercontent.com/60329288/207125392-574a903e-7f60-4e11-8b8d-dbd737f2d17f.png)

Exemplo do arquivo teste1.json:

![image](https://user-images.githubusercontent.com/60329288/206822253-1d6cfd2f-01c6-4e33-bb2b-c1c04920b61b.png)

Exemplo de comando:

![image](https://user-images.githubusercontent.com/60329288/207125615-07c81173-29ba-4b33-98b2-e286371400e0.png)

Para compilar use o comando: javac -cp .;json-simple-1.1.1.jar App (javac -cp .;../lib/json-simple-1.1.1.jar App.java) para usar o da pasta /lib
Para rodar use o comando: java -cp .;json-simple-1.1.1.jar App     (java -cp .;../lib/json-simple-1.1.1.jar App.java arquivo.json "palavra") para usar o da pasta /lib

Foi necessário utilizar a bib json-simple para fazer o projeto, por isso a importancia dela na compilação. Coloquei um arquivo na pasta raiz,lib,src e bin para não ter erro.

