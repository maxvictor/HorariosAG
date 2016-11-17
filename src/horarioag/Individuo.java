package horarioag;

import java.util.ArrayList;
import java.util.Random;

public class Individuo {

    ArrayList<String> grade, nomeMaterias;
    int semanas, horarios, qtdMaterias, aptidao;
    Random gerador;

    public Individuo() {
        grade = new ArrayList<>();
        nomeMaterias = new ArrayList<>();
        gerador = new Random();
        aptidao = 100;
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

    void adicionarMateria(String mat[]) {
        for (int i = 0; i < mat.length; i++) {
            nomeMaterias.add(mat[i]);
        }
        qtdMaterias = nomeMaterias.size();
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

    int verificaHorariosSeguidos() {
        // Verifica se existem x ou mais horários seguidos na grade
        // Retorna 0 caso existam x ou mais horários seguidos
        // Retorna 1 caso não existam
        int x = 4;
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
            if(contSeguidos[i] >= 4){
                return 0;
            }
        }
        return 1;
    }

    int avaliarGrade() {
        // Todo indivíduo inicia com aptidão igual a 100 pts
        // Perde-se pontos para cada restrição
        /*
            .: Pesos aplicados a cada critério :.
            Caso existem x horários seguidos no mesmo dia -> -5 pts
            Caso exista um horário afastado dos demais no memso dia -> -10 pts
            Deve existir no mínimo y horários de cada matéria -> -20 pts
        */
        int x = 3;
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

        return aptidao;
    }
}
