import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class BancoDeDados {

    private Mercadoria[] mercadorias;

    private int quantidade;
    public int getQuantidade() {return quantidade;}

    public BancoDeDados(String arquivoCSV) {
        mercadorias = new Mercadoria[1_000_000];
        quantidade = 0;
        carregarArquivo(arquivoCSV);
        mergeSort(mercadorias);
    }

    private void carregarArquivo(String arquivoCSV) {
        FileReader arquivo = null;
        try {
            arquivo = new FileReader(arquivoCSV);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader leitor = new BufferedReader(arquivo);
        try {
            AtomicInteger contador = new AtomicInteger(0);
            leitor.lines().map(line -> line.split(","))
            .forEach(colunas -> mercadorias[contador.getAndIncrement()] = new Mercadoria(colunas[0], colunas[1], Double.parseDouble(colunas[2])));
            quantidade = contador.get();
            leitor.close();
        } catch (IOException _) {}
    }

    public void mergeSort(Mercadoria[] arr) {
        if (arr == null || arr.length <= 1) return;

        int n = quantidade;
        Mercadoria[] temp = new Mercadoria[n];
        mergeSort(arr, temp, 0, n - 1);
    }

    private void mergeSort(Mercadoria[] arr, Mercadoria[] temp, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(arr, temp, left, middle);
            mergeSort(arr, temp, middle + 1, right);
            merge(arr, temp, left, middle, right);
        }
    }

    private void merge(Mercadoria[] arr, Mercadoria[] temp, int left, int middle, int right) {
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (temp[i].codigo().compareTo(temp[j].codigo()) <= 0) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= middle) {
            arr[k] = temp[i];
            k++;
            i++;
        }
    }

    public Mercadoria pesquisarMercadoria(String codigo) {
        int inicio = 0;
        int fim = quantidade;
        while (inicio <= fim) {
            int meio = (fim + inicio) / 2;
            int comparacao = codigo.compareTo(mercadorias[meio].codigo());
            if (comparacao < 0) {
                fim = meio - 1;
            } else if (comparacao > 0) {
                inicio = meio + 1;
            } else {
                return mercadorias[meio];
            }
        }
        return null;
    }
}
