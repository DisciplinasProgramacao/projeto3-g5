import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Estacionamento estacionamento1 = new Estacionamento("Estacionamento 1", 12, 22);
        Estacionamento estacionamento2 = new Estacionamento("Estacionamento 2", 10, 28);
        Estacionamento estacionamento3 = new Estacionamento("Estacionamento 3", 15, 25);
       // estacionamento1.carregarArquivo();
        //estacionamento2.carregarArquivo();
       // estacionamento3.carregarArquivo();
      
        int escolha;
        do {
         estacionamento1 = new Estacionamento("Estacionamento 1", 12, 22);
         estacionamento2 = new Estacionamento("Estacionamento 2", 10, 28);
         estacionamento3 = new Estacionamento("Estacionamento 3", 15, 25);
        if(estacionamento1.getId()[0]!=null) System.err.println( estacionamento1.getId()[0].getNome());
         estacionamento1.carregarArquivo();
         System.out.println("contcli_1 "+estacionamento1.contCli);
         estacionamento2.carregarArquivo();
         estacionamento3.carregarArquivo();
           System.out.println("carregando estacionamento salvo ");
            System.out.println("Menu Principal");
            System.out.println("1. Entrar como Cliente");
            System.out.println("2. Entrar como Gestor");
            System.out.println("3. Sair");

            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    entrarComoCliente(estacionamento1,estacionamento2,estacionamento3);
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

    public static void entrarComoCliente(Estacionamento estacionamento1,Estacionamento estacionamento2,Estacionamento estacionamento3) {
        Scanner scanner = new Scanner(System.in);
        Estacionamento e=estacionamento1;
        System.out.println("escolha o estacionamento: ");
        int escolha = scanner.nextInt();

         switch (escolha) {
            case 1:
                e=estacionamento1;
                break;
            case 2:
                e=estacionamento2;
                break;
            case 3:
                e=estacionamento3;
                break;
            default:
                System.out.println("Opção inválida. Escolha uma opção válida.");

                break;
        }
        
        //System.out.println("nome cliente"+ e.getId()[0].getNome()+" placa do carro"+e.getId()[0].getVeiculos()[0].getPlaca());
        
        scanner.nextLine();
        System.out.println("Indique seu identificador: ");
        String id = scanner.nextLine();
        

        menuCliente(e,id);
    }

    public static void menuCliente(Estacionamento estacionamento1,String id){
        Scanner scanner = new Scanner(System.in);
        Cliente[] arrCliente= estacionamento1.getId();
        Cliente c=estacionamento1.getId()[0];;
        boolean existe=false; 
        if(arrCliente.length>0){
        for (Cliente cliente : arrCliente) {
            if(cliente!=null && cliente.getId().equals(id)){
               c=cliente;
                existe = true;
            }
        }
    }
        if(!existe){
        System.out.println("Indique seu nome: ");
        String nome = scanner.nextLine();
         c=new Cliente(nome, id);
         estacionamento1.addCliente(c);
        }


        //Scanner scanner = new Scanner(System.in);
        int escolha;
        do{
            System.out.println("Menu Cliente");
            System.out.println("1. Estacionar Veiculo");
            System.out.println("2. Sair com veículo");
            System.out.println("3. Acessar histórico de uso do estacionamento");
            System.out.println("4. adicionar veículo");
            System.out.println("5. Sair");
            escolha = scanner.nextInt();
            scanner.nextLine();
            switch (escolha) {
                case 1:
                    estacionarVeiculo(estacionamento1);
                    break;
                case 2:
                    sairVeiculo(estacionamento1);
                    break;
                case 3:
                    ArrayList<String> historico = estacionamento1.historicoDeUso(c);
                    System.out.println(historico);
                    break;
                case 4:
                System.out.println("digite a placa do carro.");
                String placa = scanner.nextLine();
                    adicionarVeiculo(estacionamento1,id,placa);
                break;
                case 5:
                    System.out.println("Saindo do menu do cliente.");
                    System.out.println("contcli_2 "+estacionamento1.contCli);
                    apagarArquivos(estacionamento1);
                    
                    return;
                default:
                    System.out.println("Opção inválida. Escolha uma opção válida.");
                    break;
            }
        }while(escolha < 4 && escolha > 0);
    }
    public static void apagarArquivos(Estacionamento estacionamento1){
        try {
        FileWriter writer = new FileWriter("cliente.txt", false);
        writer.close();
          writer = new FileWriter("estacionamento.txt", false);
        writer.close();
          writer = new FileWriter("vaga.txt", false);
        writer.close();
          writer = new FileWriter("veiculo.txt", false);
           writer.close();
          writer = new FileWriter("usoDeVaga.txt", false);
        writer.close();
            } catch (IOException e) {
			e.printStackTrace();
		}
        finally{
            estacionamento1.escreverArquivo();
        }
    }
   public static void adicionarVeiculo(Estacionamento estacionamento,String id,String placa){
    Veiculo v= new Veiculo(placa, 20);
    estacionamento.addVeiculo(v, id);
    menuCliente(estacionamento,id);
   }

    public static void estacionarVeiculo(Estacionamento estacionamento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a placa do veiculo: ");
        String plaque = scanner.nextLine();
        System.out.println("Indique hora de entrada: ");        
        String hour = scanner.nextLine();
        LocalDateTime momentoAtual = LocalDate.now().atTime(Integer.parseInt(hour.split(":")[0]), Integer.parseInt(hour.split(":")[1]));
        estacionamento.estacionar(plaque, momentoAtual);
    }

    public static void sairVeiculo(Estacionamento estacionamento) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Indique a placa do veiculo: ");
        String plaque = scanner.nextLine();
        System.out.println("Indique hora de saida: ");        
        String hour = scanner.nextLine();
        LocalDateTime momentoAtual = LocalDate.now().atTime(Integer.parseInt(hour.split(":")[0]), Integer.parseInt(hour.split(":")[1]));
        
        estacionamento.sair(plaque, momentoAtual);
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
