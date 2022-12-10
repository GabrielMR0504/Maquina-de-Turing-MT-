import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;

public class MaquinaTuring {

    private List<Character> fita; // Fita
    private int cabecote; // cabeçote da Máquina
    private List<Character> alfabeto; // Alfabeto de entrada
    private List<Estado> estados; // Estados da maquina
    private char inicFita; // Caracter de inicio da fita
    private String palavra; // Palavra de entrada 
    private char vazio; // Caracter de vazio
    private Estado estadoAtual; // Estado atual (Comeca com estado inicial)

    public MaquinaTuring(JSONArray arrayMT, String palavra) {
        this.alfabeto = new ArrayList<>();
        this.fita = new ArrayList<>();
        this.inicFita = ((String) arrayMT.get(3)).charAt(0);
        this.vazio = ((String) arrayMT.get(4)).charAt(0);
        this.cabecote = 1;
        this.estados = new ArrayList<Estado>();
        verificaPalavra(palavra);
        atribuiEstado((JSONArray) arrayMT.get(0));
        atribuiTransissao((JSONArray) arrayMT.get(5));
        defineEst((JSONArray) arrayMT.get(7), (String) arrayMT.get(6));
        defineAlfabeto((JSONArray) arrayMT.get(1));
        defineFita(this.palavra);
    }

    public void verificaPalavra(String palavra) {
        // Trata palavra se null
        if (palavra == null) {
            this.palavra = " ";
        } else {
            this.palavra = palavra;
        }
    }
    // Preenche fita
    public void defineFita(String palavra) {
        fita.add(this.inicFita);
        char[] palavraCharArray = palavra.toCharArray();
        for (int i = 0; i < palavraCharArray.length; i++) {
            fita.add(palavraCharArray[i]);
        }
        fita.add(this.vazio);
    }

    public void defineAlfabeto(JSONArray alfabeto) {
        // Preenche alfabeto
        for (int i = 0; i < alfabeto.size(); i++) {
            this.alfabeto.add(((String) alfabeto.get(i)).charAt(0));
        }
        this.alfabeto.add(this.vazio); // Atribui vazio ao alfabeto
    }

    // Define os estados finais e iniciais
    public void defineEst(JSONArray estadosFinais, String estadoInicial) {
        for (int i = 0; i < estadosFinais.size(); i++) {
            for (int j = 0; j < estados.size(); j++) {
                if (((String) estadosFinais.get(i)).equals(estados.get(j).getNome())) {
                    estados.get(j).setEstFinal(true); //Atribui estados finais
                }

            }
        }

        for (int i = 0; i < estados.size(); i++) {
            if (estados.get(i).getNome().equals(estadoInicial)) {
                this.estadoAtual = estados.get(i); // Atribui estado inicial
            }
        }
    }
    
    // Vai associar as transissoes aos estados
    public void atribuiTransissao(JSONArray transicoes) { 
        // 0- estado, 1 - simbolo, 2 - prox estado, 3 subSimbolo, 4 - direcao

        for (int i = 0; i < estados.size(); i++) {

            for (int j = 0; j < transicoes.size(); j++) {

                // Compara primeira posicao da transicao (Nome do estado que ela pertence) com o
                // estado que esta no array da MT
                if (((String) ((JSONArray) transicoes.get(j)).get(0)).equals(estados.get(i).getNome())) {
                    char simbolo = ((String) (((JSONArray) transicoes.get(j)).get(1))).charAt(0);
                    String proxEstado = (String) ((JSONArray) transicoes.get(j)).get(2);
                    char subSimbolo = ((String) ((JSONArray) transicoes.get(j)).get(3)).charAt(0);
                    char direcao = ((String) ((JSONArray) transicoes.get(j)).get(4)).charAt(0);
                    // Cria transicao
                    Transicao transicao = new Transicao(simbolo, proxEstado, subSimbolo, direcao);
                    // Associa trasicao ao estado que ela pertence
                    estados.get(i).addTransicao(transicao);
                }
            }
        }
    }
    // Instanci os estados e salva no array
    public void atribuiEstado(JSONArray estados) {
        for (int i = 0; i < estados.size(); i++) {
            Estado est = new Estado((String) estados.get(i));
            this.estados.add(est);
        }
    }

    // Inicia execucao da MT
    public boolean rodaMaq() {
        boolean condicao = true; // Estado de condição para aceite
        boolean aceita = false; // Estado de aceitamento da palavra
        if (verificarAlfabetoPalavra()) {
            while (condicao) {
                // Consome sinbulo até não ter como mais e verifica se é estado final
                if (!consumirSimbolo()) {
                    condicao = false;
                    
                    if (estadoAtual.getEstFinal()) {
                        aceita = true; 
                    }
                }
            }
        }
        return aceita;
    }

    // Verifica se palavra pertence pertence ao alfabeto
    public boolean verificarAlfabetoPalavra() {
        char[] palavraChar = palavra.toCharArray();
        String alfabeto = this.alfabeto.toString();
        boolean verifica = true;

        for (int i = 0; i < palavraChar.length; i++) {
            if (!alfabeto.contains(palavraChar[i] + "")) {
                verifica = false;
            }
        }
        return verifica;
    }

    public boolean consumirSimbolo() {
        // Confere se posicao do cabecote nao e vazio
        if (cabecote >= fita.size()) {
            fita.add(vazio);
        }

        char simbolo = fita.get(cabecote); // pega valor onde o cabecote aponta

        // Verifica se tem alguma transicao para o simbulo
        if (estadoAtual.execTransicao(simbolo) != null) {
            Transicao t = estadoAtual.execTransicao(simbolo);
            // Trocar o simbolo na fita
            fita.set(cabecote, t.getSubSimbolo());

            // Mover cabecote
            if (t.getDirecao() == '>') {
                cabecote++;
            } else if (t.getDirecao() == '<') {
                // Se tentar ir para a esquerda no comeco da fita entao e falso
                if (fita.get(cabecote) == inicFita) {
                    return false;
                } else {
                    cabecote--;
                }
            }

            // Mudar estado
            for (int i = 0; i < estados.size(); i++) {
                if (estados.get(i).getNome().equals(t.getProxEstado())) {
                    estadoAtual = estados.get(i);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < alfabeto.size(); i++) {
            result += alfabeto.get(i);
        }

        return result;
    }

}
