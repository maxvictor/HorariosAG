package horarioag;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Individuo implements Comparable<Individuo> {

    private ArrayList<String> grade, nomeMaterias;
    private ArrayList<Integer> qtdPorMateria;
    private int semanas, horarios, qtdMaterias, aptidao;
    private Random gerador;
    private Scanner sc;

    public Individuo() {
        grade = new ArrayList<>();
        nomeMaterias = new ArrayList<>();
        gerador = new Random();
        aptidao = 200;
        sc = new Scanner(System.in);
        qtdPorMateria = new ArrayList<>();
    }

    public ArrayList<String> getGrade() {
        return grade;
    }

    public void setGrade(ArrayList<String> grade) {
        this.grade = grade;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }

    public int getHorarios() {
        return horarios;
    }

    public void setHorarios(int horarios) {
        this.horarios = horarios;
    }

    public ArrayList<String> getNomeMaterias() {
        return nomeMaterias;
    }

    public void setNomeMaterias(ArrayList<String> nomeMaterias) {
        this.nomeMaterias = nomeMaterias;
    }

    public ArrayList<Integer> getQtdPorMateria() {
        return qtdPorMateria;
    }

    public void setQtdPorMateria(ArrayList<Integer> qtdPorMateria) {
        this.qtdPorMateria = qtdPorMateria;
    }

    public int getQtdMaterias() {
        return qtdMaterias;
    }

    public void setQtdMaterias(int qtdMaterias) {
        this.qtdMaterias = qtdMaterias;
    }

    public int getAptidao() {
        return aptidao;
    }

    public void setAptidao(int aptidao) {
        this.aptidao = aptidao;
    }

    public Random getGerador() {
        return gerador;
    }

    public void setGerador(Random gerador) {
        this.gerador = gerador;
    }

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    void imprimirGrade() {
        switch (semanas) {
            case 1:
                System.out.print("|    | Seg |");
                break;
            case 2:
                System.out.print("|    | Seg | Ter |");
                break;
            case 3:
                System.out.print("|    | Seg | Ter | Qua |");
                break;
            case 4:
                System.out.print("|    | Seg | Ter | Qua | Qui |");
                break;
            case 5:
                System.out.print("|    | Seg | Ter | Qua | Qui | Sex |");
                break;
            case 6:
                System.out.print("|    | Seg | Ter | Qua | Qui | Sex | Sab |");
                break;
            case 7:
                System.out.print("|    | Seg | Ter | Qua | Qui | Sex | Sab | Dom |");
                break;
            default:
                System.out.println("Número de semanas inválido");
                break;
        }

        int j = 0;
        int s = semanas;
        for (int i = 0; i < horarios;) {
            System.out.print("\n");
            System.out.print("| " + (i + 1) + "º | ");
            for (; j < grade.size(); j++) {
                if (j >= s) {
                    break;
                }
                System.out.print(grade.get(j) + " | ");
            }
            i++;
            s += semanas;
        }

        System.out.println("");
    }

    void definirGrade(int semanas, int horarios) {
        this.semanas = semanas;
        this.horarios = horarios;
    }

    void definirQuantidadeAulas(Integer qtd[]) {
        for (int i = 0; i < qtd.length; i++) {
            qtdPorMateria.add(qtd[i]);
        }
    }

    void adicionarMateria(String mat[]) {
        for (int i = 0; i < mat.length; i++) {
            nomeMaterias.add(mat[i]);
        }
        qtdMaterias = nomeMaterias.size();
        /*
        int aulasVagas = semanas * horarios;
        for (int i = 0; i < qtdMaterias; i++) {
            System.out.println("Número de aulas vagas: " + aulasVagas);
            System.out.println("Digite a quantidade de aulas para a mátéria " + nomeMaterias.get(i) + ": ");
            qtdPorMateria[i] = sc.nextInt();
            sc.skip("\n");
        }*/
    }

    void imprimirMaterias() {
        System.out.println("Lista de Matérias: ");
        for (int i = 0; i < nomeMaterias.size(); i++) {
            if (i != (nomeMaterias.size() - 1)) {
                System.out.print(nomeMaterias.get(i) + " - ");
            } else {
                System.out.println(nomeMaterias.get(i) + "\n");
            }
        }
    }

    void gerarGradeVazia() {
        for (int i = 0; i < semanas * horarios; i++) {
            grade.add("---");
        }
    }

    void gerarGradeAleatoria() {
        //grade.clear();
        for (int i = 0; i < horarios * semanas; i++) {
            int numRand = gerador.nextInt(nomeMaterias.size());
            grade.add(nomeMaterias.get(numRand));
        }
    }

    int verificaMateriasGrade() {
        // Verifica se todas as matérias estão na grade
        // Retorna 0 caso falte matérias na grade / 1 caso esteja ok
        int contMat[] = new int[qtdMaterias];
        for (int i = 0; i < contMat.length; i++) {
            contMat[i] = 0;
        }

        for (int i = 0; i < grade.size(); i++) {
            for (int j = 0; j < qtdMaterias; j++) {
                if (nomeMaterias.get(j).equals(grade.get(i))) {
                    contMat[j]++;
                }
            }
        }

        for (int i = 0; i < contMat.length; i++) {
            if (contMat[i] == 0) {
                return 0;
            }
        }
        return 1;
    }

    int aptidao01(int penalidade) {
        // 01 - Caso existem x horários seguidos no mesmo dia -> -5 pts
        // Retorna 0 caso existam x ou mais horários seguidos
        // Retorna 1 caso não existam
        int x = penalidade;
        int contSeguidos[] = new int[semanas];

        for (int i = 0; i < contSeguidos.length; i++) {
            contSeguidos[i] = 0;
        }

        for (int k = 0; k < semanas; k++) {
            for (int i = k; i < (grade.size() - semanas); i = i + semanas) {
                if (grade.get(i).equals(grade.get(i + semanas))) {
                    contSeguidos[k]++;
                }
            }
        }

        for (int i = 0; i < contSeguidos.length; i++) {
            if (contSeguidos[i] >= x) {
                return 0;
            }
        }
        return 1;
    }

    int aptidao02() {
        // 02 - Deve existir a quantidade de matérias estipuladas
        // Retorna 0 caso não esteja ok
        // Retorna 1 caso esteja ok

        int contMat[] = new int[qtdMaterias];
        for (int i = 0; i < contMat.length; i++) {
            contMat[i] = 0;
        }

        for (int i = 0; i < grade.size(); i++) {
            for (int j = 0; j < qtdMaterias; j++) {
                if (nomeMaterias.get(j).equals(grade.get(i))) {
                    contMat[j]++;
                }
            }
        }

        int cont = 0;
        for (int i = 0; i < contMat.length; i++) {
            if (contMat[i] != qtdPorMateria.get(i)) {
                cont++;
            }
        }
        
        if(cont == 0){
            return 1;
        }
        
        return 0;
    }
    
    void aptidao03(){
        // 03 - verifica a quantidade de materias na grade e compara com o desejado.
        // Retira 10 pontos para cada quantidade que não se adeque.
        
        int contMat[] = new int[qtdMaterias];
        for (int i = 0; i < contMat.length; i++) {
            contMat[i] = 0;
        }

        for (int i = 0; i < grade.size(); i++) {
            for (int j = 0; j < qtdMaterias; j++) {
                if (nomeMaterias.get(j).equals(grade.get(i))) {
                    contMat[j]++;
                }
            }
        }

        int cont = 0;
        for (int i = 0; i < contMat.length; i++) {
            if (contMat[i] != qtdPorMateria.get(i)) {
                cont++;
            }
        }
        
        if(cont != 0){
            aptidao -= cont*10;
        }
    }

    void avaliarGrade() {
        // Todo indivíduo inicia com aptidão igual a 200 pts
        // Perde-se pontos para cada restrição
        if (aptidao01(3) == 0) {
            aptidao -= 5;
        }
        if (aptidao01(4) == 0) {
            aptidao -= 15;
        }
        if (aptidao02() == 0) {
            aptidao -= 30;
        }
        aptidao03();
    }
    
    void mutacao(){
        int numRand = gerador.nextInt(grade.size());
        int numRandMateria = gerador.nextInt(nomeMaterias.size());
        
        grade.set(numRand, nomeMaterias.get(numRandMateria));
    }

    @Override
    public int compareTo(Individuo outroIndividuo) {
        if (this.aptidao > outroIndividuo.aptidao) {
            return -1;
        }
        if (this.aptidao < outroIndividuo.aptidao) {
            return 1;
        }
        return 0;
    }
}
