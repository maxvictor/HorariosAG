package horarioag;

import java.util.ArrayList;
import java.util.Random;

public class HorarioAG {

    public static void main(String[] args) {
        int qtdPopi = 100;
        ArrayList<Individuo> popi = new ArrayList<>();
        Individuo ind = new Individuo();
        String materias[] = {"Por", "Mat", "Geo", "His", "Bio", "Fis"};
        Integer qtdPorMateria[] = {5, 5, 3, 3, 5, 4};
        Integer selTorneio[] = new Integer[qtdPopi];
        int linha = 5, coluna = 5, soma = 0;
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
            
            if(aptidao1 >= aptidao2)
                selTorneio[i] = escolhido1;
            else
                selTorneio[i] = escolhido2;
            
            System.out.println(selTorneio[i]);
        }
        
        // Realizar Crossover
        
        

        /*
        Representar o Indivíduo
        Gerar a população inicial de forma aleatória
        2. Avaliar cada indivíduo da população.
        3. Enquanto critério de parada não for satisfeito
        faça
        3.1 Selecionar os indivíduos mais aptos.
         3.2 Criar novos indivíduos aplicando os
         operadores crossover e mutação.
         3.3 Armazenar os novos indivíduos em uma
         nova população.
         3.4 Avaliar cada indivíduo da nova
         população.
         */
    }

}
