package br.edu.ifsp.arq.tsi.inoo.locacao.view;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

//import br.edu.ifsp.arq.tsi.inoo.locacao.controller.LocacaoController;
//import br.edu.ifsp.arq.tsi.inoo.locacao.controller.CarrosController;
//import br.edu.ifsp.arq.tsi.inoo.locacao.controller.PessoaController;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.Carro;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.Locacao;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.PessoaFisica;
import br.edu.ifsp.arq.tsi.inoo.locacao.model.PessoaJuridica;
//import br.edu.ifsp.arq.tsi.inoo.locacao.controller.LocacaoController;;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        ArrayList<PessoaFisica> pessoasFisicasLocacao = new ArrayList<>();
        ArrayList<PessoaJuridica> pessoasJuridicasLocacao = new ArrayList<>();
        ArrayList<Carro> carrosLocacao = new ArrayList<>();
        ArrayList<Locacao> locacoes = new ArrayList<>();

        while (continuar) {
            System.out.println("\nBem-vindo! O que você deseja fazer?");
            System.out.println("1. Cadastrar Pessoa Física");
            System.out.println("2. Cadastrar Pessoa Jurídica");
            System.out.println("3. Cadastrar Carro");
            System.out.println("4. Realizar Locação");
            System.out.println("5. Realizar Devolução");
            System.out.println("6. Gerar Relatório");
            System.out.println("7. Sair do Programa");

            System.out.println("Escolha uma opção:");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    boolean cpfDuplicado = true;
                    while (cpfDuplicado) {
                        System.out.println("Digite o nome da Pessoa Física:");
                        String nomePF = scanner.nextLine();
                        System.out.println("Digite o CPF da Pessoa Física:");
                        String cpf = scanner.nextLine();

                        try {
                            PessoaFisica pessoaFisica = new PessoaFisica(1, nomePF, cpf);
                            pessoasFisicasLocacao.add(pessoaFisica);
                            System.out.println("Pessoa Física cadastrada com sucesso: " + pessoaFisica);
                            cpfDuplicado = false;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Erro: " + e.getMessage());
                            System.out.println("Por favor, digite um CPF válido!");
                        }
                    }
                    break;
                case 2:
                    boolean cnpjDuplicado = true;
                    while (cnpjDuplicado) {
                        System.out.println("Digite o nome da Pessoa Jurídica:");
                        String nomePJ = scanner.nextLine();
                        System.out.println("Digite o CNPJ da Pessoa Jurídica:");
                        String cnpj = scanner.nextLine();

                        if (verificarCnpjDuplicado(pessoasJuridicasLocacao, cnpj)) {
                            System.out.println("CNPJ já cadastrado! Por favor, insira um CNPJ diferente!");
                        } else {
                            System.out.println("Digite o nome da empresa:");
                            String nomeEmpresa = scanner.nextLine();
                            PessoaJuridica pessoaJuridica = new PessoaJuridica(1, nomePJ, cnpj, nomeEmpresa);
                            pessoasJuridicasLocacao.add(pessoaJuridica);
                            System.out.println("Pessoa Jurídica cadastrada com sucesso: " + pessoaJuridica);
                            cnpjDuplicado = false;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Digite a marca do carro:");
                    String marca = scanner.nextLine();
                    System.out.println("Digite o modelo do carro:");
                    String modelo = scanner.nextLine();
                    System.out.println("Digite o ano do carro:");
                    int ano = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.println("Digite a placa do carro:");
                    String placa = scanner.nextLine();
                    System.out.println("Digite a capacidade de passageiros do carro:");
                    int capacidadePassageiros = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.println("Digite o valor da diária do carro:");
                    double valorDiaria = scanner.nextDouble();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.println("O carro possui ar condicionado? (S/N):");
                    String temArCondicionado = scanner.nextLine();
                    boolean arCondicionado = temArCondicionado.equalsIgnoreCase("S");

                    Carro carro = new Carro(1, marca, modelo, ano, placa, capacidadePassageiros, arCondicionado,
                            valorDiaria, true);
                    carrosLocacao.add(carro);
                    System.out.println("Carro cadastrado com sucesso: " + carro);
                    break;

                case 4:
                    realizarLocacao(pessoasFisicasLocacao, pessoasJuridicasLocacao, carrosLocacao, locacoes, scanner);
                    break;
                case 5:
                    realizarDevolucao(locacoes, carrosLocacao);
                    break;
                case 6:
                    exibirRelatorioLocacoes(locacoes);
                    scanner.nextLine(); // Limpar o buffer
                    break;
                case 7:
                    System.out.println("Saindo do programa...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida! Por favor, escolha uma opção válida!");
                    break;

            }
        }
        scanner.close();
    }

    public static void realizarLocacao(ArrayList<PessoaFisica> pessoasFisicasLocacao,
            ArrayList<PessoaJuridica> pessoasJuridicasLocacao,
            ArrayList<Carro> carrosLocacao,
            ArrayList<Locacao> locacoes,
            Scanner scanner) {

        System.out.println("Selecione o tipo de cliente para a locação:");
        System.out.println("1. Pessoa Física");
        System.out.println("2. Pessoa Jurídica");
        int tipoCliente = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        ArrayList<Carro> carrosDisponiveis = obterCarrosDisponiveis(carrosLocacao);

        if (tipoCliente == 1) {
            System.out.println("Selecione a Pessoa Física para a locação:");
            for (int i = 0; i < pessoasFisicasLocacao.size(); i++) {
                System.out.println((i + 1) + ". " + pessoasFisicasLocacao.get(i).getNome());
            }
            int indexPF = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            System.out.println("Selecione o carro desejado:");
            for (int i = 0; i < carrosDisponiveis.size(); i++) {
                System.out.println((i + 1) + ". " + carrosDisponiveis.get(i).getModelo());
            }
            int indexCarro = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            if (indexCarro <= carrosDisponiveis.size() && indexCarro > 0) {
                Carro carroSelecionado = carrosDisponiveis.get(indexCarro - 1);
                PessoaFisica pessoaFisica = pessoasFisicasLocacao.get(indexPF - 1);

                Locacao novaLocacao = new Locacao(pessoaFisica, carroSelecionado, LocalDate.now());
                locacoes.add(novaLocacao);

                carroSelecionado.setDisponivel(false);
                System.out.println("Locação realizada com sucesso para " + pessoaFisica.getNome() +
                        " - Carro: " + carroSelecionado.getModelo() + " - Número da Locação: "
                        + novaLocacao.getNumero());
            } else {
                System.out.println("Índice de carro inválido!");
            }
        } else if (tipoCliente == 2) {
            System.out.println("Selecione a Pessoa Jurídica para a locação:");
            for (int i = 0; i < pessoasJuridicasLocacao.size(); i++) {
                System.out.println((i + 1) + ". " + pessoasJuridicasLocacao.get(i).getNome());
            }
            int indexPJ = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            System.out.println("Selecione o carro desejado:");
            for (int i = 0; i < carrosDisponiveis.size(); i++) {
                System.out.println((i + 1) + ". " + carrosDisponiveis.get(i).getModelo());
            }
            int indexCarro = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            if (indexCarro <= carrosDisponiveis.size() && indexCarro > 0) {
                Carro carroSelecionado = carrosDisponiveis.get(indexCarro - 1);
                PessoaJuridica pessoaJuridica = pessoasJuridicasLocacao.get(indexPJ - 1);

                Locacao novaLocacaoPJ = new Locacao(pessoaJuridica, carroSelecionado, LocalDate.now());
                locacoes.add(novaLocacaoPJ);

                carroSelecionado.setDisponivel(false);
                System.out.println("Locação realizada com sucesso para " + pessoaJuridica.getNome() +
                        " - Carro: " + carroSelecionado.getModelo() + " - Número da Locação: "
                        + novaLocacaoPJ.getNumero());
            } else {
                System.out.println("Índice de carro inválido!");
            }
        } else {
            System.out.println("Opção inválida!");
        }
    }

    public static ArrayList<Carro> obterCarrosDisponiveis(ArrayList<Carro> carrosLocacao) {
        ArrayList<Carro> carrosDisponiveis = new ArrayList<>();
        for (Carro carro : carrosLocacao) {
            if (carro.isDisponivel()) {
                carrosDisponiveis.add(carro);
            }
        }
        return carrosDisponiveis;
    }

    private static boolean verificarCnpjDuplicado(ArrayList<PessoaJuridica> pessoas, String cnpj) {
        for (PessoaJuridica pessoa : pessoas) {
            if (pessoa.getCnpj().equals(cnpj)) {
                return true; // CNPJ já existe na lista
            }
        }
        return false; // CNPJ não existe na lista
    }

    public static void exibirRelatorioLocacoes(ArrayList<Locacao> locacoes) {
        System.out.println("\nRelatório de Locações:");
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-20s | %-15s | %-15s | %-12s | %-12s | %-10s\n",
                "Nome Cliente",
                "Carro",
                "Data Locação",
                "Data Devolução",
                "Valor Diária",
                "Valor Total");

        for (Locacao locacao : locacoes) {
            String nome = locacao.getPessoa().getNome();
            String modeloCarro = locacao.getCarro().getModelo();
            LocalDate dataLocacao = locacao.getDataLocacao();
            String dataLocacaoFormatada = dataLocacao.toString(); // Converte LocalDate para String no formato padrão

            LocalDate dataDevolucao = locacao.getDataDevolucao();
            String dataDevolucaoFormatada = (dataDevolucao != null) ? dataDevolucao.toString() : "Em aberto";
            double valorDiaria = locacao.getCarro().getValorDiaria();
            long diasLocados = ChronoUnit.DAYS.between(locacao.getDataLocacao(), LocalDate.now());
            double valorTotal = valorDiaria * diasLocados;

            System.out.printf("%-20s | %-15s | %-15s | %-12s | %-12.2f | %-10.2f\n",
                    nome,
                    modeloCarro,
                    dataLocacaoFormatada,
                    dataDevolucaoFormatada,
                    valorDiaria,
                    valorTotal);
        }
    }

    private static void realizarDevolucao(ArrayList<Locacao> locacoes, ArrayList<Carro> carrosLocacao) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Informe o número da locação a ser devolvida: ");
            int numeroLocacao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer
    
            Locacao locacaoEncontrada = null;
            for (Locacao locacao : locacoes) {
                if (locacao.getNumero() == numeroLocacao) {
                    locacaoEncontrada = locacao;
                    break;
                }
            }
    
            if (locacaoEncontrada != null) {
                if (!locacaoEncontrada.isDevolvido()) {
                    System.out.println("Informe a data de devolução (no formato yyyy-mm-dd): ");
                    String dataDevolucaoStr = scanner.nextLine();
                    LocalDate dataDevolucao = LocalDate.parse(dataDevolucaoStr);
    
                    locacaoEncontrada.setDataDevolucao(dataDevolucao);
    
                    // Marcar o carro associado à locação como disponível
                    Carro carroDevolvido = locacaoEncontrada.getCarro();
                    carroDevolvido.setDisponivel(true);
    
                    locacaoEncontrada.setDevolvido(true);
                    System.out.println("Devolução realizada com sucesso para a locação número " + numeroLocacao);
                } else {
                    System.out.println("Este carro já foi devolvido anteriormente.");
                }
            } else {
                System.out.println("Locação não encontrada!");
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao realizar a devolução: " + e.getMessage());
        }
    }
    

}
