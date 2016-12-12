package horarioag;

import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HorarioAG {

    public static void main(String[] args) {
        int qtdPopi = 100;
        ArrayList<Individuo> popi = new ArrayList<>();
        ArrayList<Individuo> popi2 = new ArrayList<>();
        ArrayList<Individuo> popPorAptidao = new ArrayList<>();
        Individuo ind = new Individuo();
        String materias[] = {"Por", "Mat", "Geo", "His", "Bio", "Fis"};
        Integer qtdPorMateria[] = {5, 5, 3, 3, 5, 4};
        Integer selTorneio[] = new Integer[qtdPopi];
        int linha = 5, coluna = 5, soma = 0;
        double taxaCrossOver = 0.2, taxaMutacao = 5;
        int pontoDeCorte = (int) floor((linha * coluna) / 2);
        Random gerador = new Random();

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
        int cont = 0;
        while (cont < 100) {
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

            // Passar os melhores indivíduos para a nova geração
            popPorAptidao = popi;
            Collections.sort(popPorAptidao); // ordena o array de acordo com a aptidão

            // Preenche o array popi2 com os melhores individuos da população anterior
            for (int i = 0; i < (qtdPopi * taxaCrossOver); i++) {
                popi2.add(popPorAptidao.get(i));
            }

            // Realizar Crossover
            while (popi2.size() != qtdPopi) {

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

            cont++;
        }
    }
}
