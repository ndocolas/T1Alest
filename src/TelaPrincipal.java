
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TelaPrincipal extends JFrame implements ActionListener {
    public static final String MENU_PRINCIPAL_ABRIR = "menuPrincipalAbrir";
    public static final String MENU_PRINCIPAL_SAIR = "menuPrincipalSair";
    public static final String BOTAO_PESQUISAR = "btnPesquisarClick";
    private JPanel painelTopo;
    private JPanel painelGeral;
    private JPanel painelRodape;
    private JMenuBar menuBar;
    private JTextArea textArea;
    private BancoDeDados bd;
    private JPanel painelPesquisa;
    private JTextField txtCodigo;

    public TelaPrincipal() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("ALEST1 - Controle de Mercadorias");
        setLayout(new BorderLayout());

        painelTopo = new JPanel();
        add(painelTopo, BorderLayout.NORTH);

        painelPesquisa = new JPanel();
        JLabel lblCodigo = new JLabel("Código da Mercadoria: ");
        txtCodigo = new JTextField(10);
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setActionCommand(BOTAO_PESQUISAR);
        btnPesquisar.addActionListener(this);
        painelPesquisa.add(lblCodigo);
        painelPesquisa.add(txtCodigo);
        painelPesquisa.add(btnPesquisar);
        painelPesquisa.setVisible(false);
        painelTopo.add(painelPesquisa);

        painelRodape = new JPanel();
        add(painelRodape, BorderLayout.SOUTH);

        painelGeral = new JPanel();
        add(painelGeral, BorderLayout.CENTER);
        painelGeral.setBackground(Color.WHITE);

        textArea = new JTextArea();
        painelGeral.add(textArea, BorderLayout.WEST);

        JLabel labelAutor = new JLabel("PUCRS - Escola Politécnica");
        painelRodape.add(labelAutor);

        construirMenu();
        setVisible(true);
    }

    private void construirMenu() {
        menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem menuItemAbrir = new JMenuItem("Abrir");
        JMenuItem menuItemSair = new JMenuItem("Sair");

        menuItemAbrir.setActionCommand(MENU_PRINCIPAL_ABRIR);
        menuItemAbrir.addActionListener(this);

        menuItemSair.setActionCommand(MENU_PRINCIPAL_SAIR);
        menuItemSair.addActionListener(this);

        menuArquivo.add(menuItemAbrir);
        menuArquivo.add(menuItemSair);

        menuArquivo.addActionListener(this);
        menuBar.add(menuArquivo);
        this.setJMenuBar(menuBar);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(MENU_PRINCIPAL_SAIR)) {
            System.exit(0);
        }
        else if(e.getActionCommand().equals(MENU_PRINCIPAL_ABRIR)) {
            JFileChooser seletorDeArquivo = new JFileChooser(".");
            int opcao = seletorDeArquivo.showOpenDialog(this);
            if(opcao==JFileChooser.APPROVE_OPTION) {
                File arquivoSelecionado = seletorDeArquivo.getSelectedFile();
                abrirBancoDeDados(arquivoSelecionado);
            }
            else {
                //labelMensagens.setText("abrir arquivo cancelado");
            }
        }
        else if(e.getActionCommand().equals(BOTAO_PESQUISAR)) {
            pesquisar(txtCodigo.getText().toUpperCase());
        }
    }

    private void abrirBancoDeDados(File arquivoSelecionado) {
        textArea.append("Carregando banco de dados...");
        long ti = System.currentTimeMillis();
        bd = new BancoDeDados(arquivoSelecionado.getAbsolutePath());
        long tf = System.currentTimeMillis();
        long tempo = tf - ti;
        textArea.append(System.lineSeparator());
        textArea.append("Banco de Dados carregado com sucesso. ");
        textArea.append(System.lineSeparator());
        textArea.append("Total de " + bd.getQuantidade() + " linhas.");
        textArea.append(System.lineSeparator());
        textArea.append("Tempo para carregar (SEGUNDOS): " + tempo/1000 + ".");
        textArea.append(System.lineSeparator());
        painelPesquisa.setVisible(true);
        txtCodigo.requestFocus();
    }

    private void pesquisar(String chave) {
        textArea.append(System.lineSeparator());
        textArea.append("-".repeat(100));
        textArea.append(System.lineSeparator());
        textArea.append("Pesquisando o código " + chave + "...");

        Mercadoria m = null;
        long ti = System.currentTimeMillis();
        for (int i = 0; i < bd.getQuantidade(); i++) m = bd.pesquisarMercadoria(chave);
        long tf = System.currentTimeMillis();
        long tempo = tf - ti;
        if(m==null) {
            textArea.append(System.lineSeparator() + "Codigo " + chave + " não localizado!");
        }
        else {
            textArea.append(System.lineSeparator() + "Mercadoria localizada com sucesso!");
            textArea.append(System.lineSeparator() + "Código...: " + m.getCodigo());
            textArea.append(System.lineSeparator() + "Descrição: " + m.getDescricao());
            textArea.append(System.lineSeparator() + "Preço....: " + m.getPreco());
        }
        textArea.append(System.lineSeparator() + "Tempo de pesquisa (SEGUNDOS): " + tempo/1000 );
    }
}
