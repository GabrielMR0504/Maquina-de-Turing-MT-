
import java.util.ArrayList;
import java.util.List;

public class Estado {

    private String nome;    
    private List<Transicao> transicoes; // Trans. ref. ao estado 
    private boolean estFinal; // Especifica se o estado é final

    public Estado (String nome) {
        this.transicoes = new ArrayList<>();
        this.nome = nome;
        this.estFinal = false;
    }
    public String getNome(){
        return this.nome;
    }
    public boolean getEstFinal(){
        return this.estFinal;
    }
    public void addTransicao(Transicao transicao){
        this.transicoes.add(transicao);
    }
    public void setEstFinal(boolean estFinal) {
        this.estFinal = estFinal;
    }

    public String toString () {
        String result = "Estado: "+nome + "\n";
        for(Transicao t: transicoes) {
            result += t.toString() + "\n";
        }
        return result;
    }
    
    //Retorna transicao referente ao simbolo
    public Transicao execTransicao (char simbolo){
        Transicao transicao = null;
        for(Transicao t:transicoes) {
            if(t.getSimbolo() == simbolo) {
                transicao = t;
            }
        }
        return transicao;
    }



}