import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estacionamento estacionamento1 = new Estacionamento("Estacionamento 1", 12, 22);
        Estacionamento estacionamento2 = new Estacionamento("Estacionamento 2", 10, 28);
        Estacionamento estacionamento3 = new Estacionamento("Estacionamento 3", 15, 25);
        int escolha;
        do {
            System.out.println("Menu Principal");
            System.out.println("1. Entrar como Cliente");
            System.out.println("2. Entrar como Gestor");
            System.out.println("3. Sair");

            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    entrarComoCliente(estacionamento1);
                    break;
                case 2:
                    entrarComoGestor(estacionamento1, estacionamento2, estacionamento3);
                    break;
                case 3:
                    System.out.println("Saindo do programa.");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
                    break;
            }
        }while (escolha < 4 && escolha > 0);
    }

    public static void entrarComoCliente(Estacionamento estacionamento1) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique seu identificador: ");
        String id = scanner.nextLine();
        menuCliente(estacionamento1);
    }

    public static void menuCliente(Estacionamento estacionamento1){
        Scanner scanner = new Scanner(System.in);
        int escolha;
        do{
            System.out.println("Menu Cliente");
            System.out.println("1. Estacionar Veiculo");
            System.out.println("2. Sair com veículo");
            System.out.println("3. Acessar histórico de uso do estacionamento");
            System.out.println("4. Sair");
            escolha = scanner.nextInt();
            switch (escolha) {
                case 1:
                    estacionarVeiculo(estacionamento1);
                    break;
                case 2:
                    sairVeiculo(estacionamento1);
                    break;
                    case 3:
                    ArrayList<String> historico = estacionamento1.historicoDeUso();
                    System.out.println(historico);
                    break;
                case 4:
                    System.out.println("Saindo do menu do cliente.");
                    return;
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
                    break;
            }
        }while(escolha < 4 && escolha > 0);
    }

    public static void estacionarVeiculo(Estacionamento estacionamento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a placa do veiculo: ");
        String plaque = scanner.nextLine();
        estacionamento.estacionar(plaque);
    }

    public static void sairVeiculo(Estacionamento estacionamento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a placa do veiculo: ");
        String plaque = scanner.nextLine();
        estacionamento.sair(plaque);
    }
    public static void entrarComoGestor(Estacionamento estacionamento1, Estacionamento estacionamento2, Estacionamento estacionamento3) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual estacionamento você quer gerenciar(1 a 3)?");
        int num = scanner.nextInt();
        Estacionamento estacionamento = new Estacionamento(null, num, num);

        if(num == 1){
            estacionamento = estacionamento1;
        }
        if(num == 2){
            estacionamento = estacionamento2;
        }
        if(num == 3){
            estacionamento = estacionamento3;
        }
        int escolha;
        do{
            System.out.println("Menu Gestor");
            System.out.println("1. Ver valor total arrecadado");
            System.out.println("2. Ver valor arrecadado em mês específico");
            System.out.println("3. Ver valor médio de cada utilização do estacionamento");
            System.out.println("4. Gerar ranking dos clientes que mais geraram arrecadação em um determinado mês");
            System.out.println("5. Sair");
            escolha = scanner.nextInt();
            int mes;
            switch (escolha) {
                case 1:
                    System.out.println(estacionamento.totalArrecadado());
                    break;
                case 2:
                    System.out.println("Insira o mês desejado(1 a 12)");
                    mes = scanner.nextInt();
                    System.out.println(estacionamento.arrecadacaoNoMes(mes));
                    break;
                case 3:
                    System.out.println(estacionamento.valorMedioPorUso());
                    break;
                case 4:
                    System.out.println("Insira o mês desejado(1 a 12)");
                    mes = scanner.nextInt();
                    System.out.println(estacionamento.top5Clientes(mes));
                    break;
                case 5:
                    System.out.println("Saindo do programa.");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
                    break;
            }
        }while(escolha < 4 && escolha > 0);
    }
}
