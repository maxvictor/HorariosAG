package horarioag;

import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class HorarioAG {

    public static void main(String[] args) {
        int qtdPopi = 1000;
        ArrayList<Individuo> popi = new ArrayList<>();
        ArrayList<Individuo> popi2 = new ArrayList<>();
        ArrayList<Individuo> popPorAptidao = new ArrayList<>();
        ArrayList<Float> medias = new ArrayList<>();
        ArrayList<Integer> melhor = new ArrayList<>();
        String materias[];
        Integer qtdPorMateria[];
        int qtdMaterias;
        Integer selTorneio[] = new Integer[qtdPopi];
        int linha, coluna, soma = 0, criterioDeParada = 2000;
        double taxaElitismo = 0.2, taxaMutacao = 10;
        int qtdTotalDeAulas;
        Grafico gc = new Grafico();

        Random gerador = new Random();
        Scanner sc = new Scanner(System.in);

        /*
        System.out.println("Digite a quantidade de dias: ");
        coluna = sc.nextInt();
        sc.skip("\n");
        System.out.println("Digite a quantidade de horários: ");
        linha = sc.nextInt();
        sc.skip("\n");
        qtdTotalDeAulas = linha * coluna;
        System.out.println("Digite a quantidade de matérias: ");
        qtdMaterias = sc.nextInt();
        sc.skip("\n");
        materias = new String[qtdMaterias];
        qtdPorMateria = new Integer[qtdMaterias];
        for (int i = 0; i < qtdMaterias; i++) {
            System.out.println("Digite o nome da matéria número " + (i + 1) + ": ");
            materias[i] = sc.nextLine();
        }
        for (int i = 0; i < qtdMaterias; i++) {
            System.out.println("Você ainda pode ter " + qtdTotalDeAulas + " aulas");
            System.out.println("Digite a quantidade de aulas para a mátéria " + materias[i] + ": ");
            qtdPorMateria[i] = sc.nextInt();
            sc.skip("\n");
            qtdTotalDeAulas -= qtdPorMateria[i];
        }
         */
        coluna = 5;
        linha = 5;
        qtdTotalDeAulas = linha * coluna;
        qtdMaterias = 6;
        materias = new String[qtdMaterias];
        qtdPorMateria = new Integer[qtdMaterias];
        materias[0] = "Por";
        materias[1] = "Mat";
        materias[2] = "Geo";
        materias[3] = "His";
        materias[4] = "Fis";
        materias[5] = "Ing";
        qtdPorMateria[0] = 3;
        qtdPorMateria[1] = 5;
        qtdPorMateria[2] = 5;
        qtdPorMateria[3] = 5;
        qtdPorMateria[4] = 5;
        qtdPorMateria[5] = 2;

        int pontoDeCorte = (int) floor((linha * coluna) / 2);

        for (int i = 0; i < qtdPorMateria.length; i++) {
            soma += qtdPorMateria[i];
        }
        if (soma != (linha * coluna)) {
            System.out.println("-------------------------------");
            System.out.println("Quantidade de aulas não aceita.");
            System.out.println("-------------------------------");
            System.exit(0);
        }

        // Gerar população inicial
        for (int i = 0; i < qtdPopi; i++) {
            Individuo indTemp = new Individuo();
            indTemp.definirGrade(linha, coluna);
            indTemp.adicionarMateria(materias);
            indTemp.definirQuantidadeAulas(qtdPorMateria);
            indTemp.gerarGradeAleatoria();
            indTemp.avaliarGrade();

            // Validação (Verifica se todas as matérias estão em cada grade gerada)
            if (indTemp.verificaMateriasGrade() == 1) {
                popi.add(indTemp);
            } else {
                i--;
            }
        }

        // Estabelecendo critério de parada
        int cont = 1;
        while (cont <= criterioDeParada) {

            System.out.println("Geração: " + cont);

            if (!popi2.isEmpty()) {
                popi = popi2;
            }

            // Seleção Torneio
            int escolhido1, escolhido2;
            float aptidao1, aptidao2;
            for (int i = 0; i < qtdPopi; i++) {
                int numRand1 = gerador.nextInt(qtdPopi);
                escolhido1 = numRand1;
                int numRand2 = gerador.nextInt(qtdPopi);
                escolhido2 = numRand2;
                aptidao1 = popi.get(escolhido1).getAptidao();
                aptidao2 = popi.get(escolhido2).getAptidao();

                if (aptidao1 >= aptidao2) {
                    selTorneio[i] = escolhido1;
                } else {
                    selTorneio[i] = escolhido2;
                }
            }

            // Ordenando os melhores individuos por aptidao de Popi
            popPorAptidao = popi;
            Collections.sort(popPorAptidao); // ordena o array de acordo com a aptidão

            // Imprime melhor indivíduo quando chegar no critério de parada
            if (cont == criterioDeParada) {
                System.out.println("********************************");
                System.out.println("Melhor grade: ");
                popPorAptidao.get(0).imprimirGrade();
                System.out.println("********************************");
                gc.gerarGraficoMedia(medias);
                gc.gerarGraficoMelhor(melhor);
            }
            // Imprime média de aptidão por geração
            float mediaAptidaoGeracao = 0;
            for (int i = 0; i < qtdPopi; i++) {
                mediaAptidaoGeracao += popPorAptidao.get(i).getAptidao();
            }
            mediaAptidaoGeracao = mediaAptidaoGeracao / qtdPopi;
            System.out.println("Média de Aptidão da Geração: " + mediaAptidaoGeracao);
            medias.add(mediaAptidaoGeracao);

            // Imprime a aptidão do melhor indivíduo da geração
            System.out.println("Aptidão do melhor indivíduo: " + popPorAptidao.get(0).getAptidao());
            melhor.add(popPorAptidao.get(0).getAptidao());
            System.out.println("-------------------------------");

            // Passar os melhores indivíduos para a nova geração
            popi2 = new ArrayList<>();
            for (int i = 0; i < (qtdPopi * taxaElitismo); i++) {
                popi2.add(popPorAptidao.get(i));
            }

            // Realizar Crossover
            while (popi2.size() < qtdPopi) {

                int i = 0;
                Individuo indTemp = new Individuo();
                Individuo indTemp2 = new Individuo();
                indTemp.definirGrade(linha, coluna);
                indTemp.adicionarMateria(materias);
                indTemp.definirQuantidadeAulas(qtdPorMateria);
                indTemp2.definirGrade(linha, coluna);
                indTemp2.adicionarMateria(materias);
                indTemp2.definirQuantidadeAulas(qtdPorMateria);

                ArrayList<String> grade1 = new ArrayList<>();
                ArrayList<String> grade2 = new ArrayList<>();
                ArrayList<String> filho1 = new ArrayList<>();
                ArrayList<String> filho2 = new ArrayList<>();

                grade1 = popi.get(selTorneio[i]).getGrade();
                grade2 = popi.get(selTorneio[(i + 1)]).getGrade();

                for (int m = 0; m < (linha * coluna); m++) {
                    filho1.add("---");
                    filho2.add("---");
                }

                for (int j = 0, k = (linha * coluna) - 1; j <= pontoDeCorte; j++, k--) {
                    filho1.set(j, grade1.get(j));
                    filho1.set(k, grade2.get(k));

                    filho2.set(j, grade2.get(j));
                    filho2.set(k, grade1.get(k));
                }
                
                indTemp.setGrade(filho1);
                indTemp2.setGrade(filho2);
                
                // Mutacao
                int randMutacao = gerador.nextInt(100);
                int randMutacao2 = gerador.nextInt(100);

                if (randMutacao <= taxaMutacao) {
                    indTemp.mutacao();
                }

                if (randMutacao2 <= taxaMutacao) {
                    indTemp2.mutacao();
                }
                // fim mutacao

                indTemp.avaliarGrade();
                indTemp2.avaliarGrade();

                // Validação (Verifica se todas as matérias estão em cada grade gerada)
                if (indTemp.verificaMateriasGrade() == 1) {
                    popi2.add(indTemp);
                } else {
                    popi2.add(popi.get(i));
                }

                if (indTemp2.verificaMateriasGrade() == 1) {
                    popi2.add(indTemp2);
                } else {
                    popi2.add(popi.get(i));
                }

                i += 2;
            }
            cont += 1;
        }
    }
}
