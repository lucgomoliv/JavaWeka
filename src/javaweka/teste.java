/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaweka;
import java.util.Random;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.DenseInstance;

/**
 *
 * @author luqui
 */
public class teste {
    private Instances dados;
    private String caminhoDados;
    
    public teste(String caminho){
        caminhoDados = caminho;
    }
    
    public void leDados() throws Exception {
        DataSource fonte = new DataSource(caminhoDados);
        dados = fonte.getDataSet();
        if (dados.classIndex() == -1)
        dados.setClassIndex(dados.numAttributes() - 1);
    }
    
    public void imprimeDados() {
        for (int i = 0; i < dados.numInstances(); i++) {
        Instance atual = dados.instance(i);
        System.out.println((i + 1) + ": " + atual + "\n");
        }
    }
    
    public double[] arvoreDeDecisaoJ48() throws Exception {
        J48 tree = new J48();
        tree.buildClassifier(dados);
        System.out.println(tree);
        System.out.println("Avaliacao inicial: \n");
        Evaluation avaliacao;
        avaliacao = new Evaluation(dados);
        avaliacao.evaluateModel(tree, dados);
        var inicial = avaliacao.correct();
        System.out.println("--> Instancias corretas: " +
        inicial + "\n");
        System.out.println("Avaliacao cruzada: \n");
        Evaluation avalCruzada;
        avalCruzada = new Evaluation(dados);
        avalCruzada.crossValidateModel(tree, dados, 10, new
        Random(1));
        var cruzada = avalCruzada.correct();
        System.out.println("--> Instancias corretas CV: " +
        cruzada + "\n");
        return new double[]{inicial, cruzada};
    }
    
    public String InstanceBased(int GOLDEARNED, int TOTALMINIONSKILLED, int KILLS, int ASSISTS, int DEATHS, String CHAMPION, int VISIONSCORE, int TOTALDAMAGEDEALTTOCHAMPIONS) throws Exception{
        IBk k3 = new IBk(3);
        k3.buildClassifier(dados);
        Instance newInst = new DenseInstance(8);
        newInst.setDataset(dados);
        newInst.setValue(0, GOLDEARNED);
        newInst.setValue(1, TOTALMINIONSKILLED);
        newInst.setValue(2, KILLS);
        newInst.setValue(3, ASSISTS);
        newInst.setValue(4, DEATHS);
        newInst.setValue(5, CHAMPION);
        newInst.setValue(6, VISIONSCORE);
        newInst.setValue(7, TOTALDAMAGEDEALTTOCHAMPIONS);
        double pred = k3.classifyInstance(newInst);
        System.out.println("Predi????o: " + pred);
        Attribute a = dados.attribute(4);
        String predClass = a.value((int) pred);
        System.out.println("Predi????o: " + predClass);
        return predClass;
    }
}
