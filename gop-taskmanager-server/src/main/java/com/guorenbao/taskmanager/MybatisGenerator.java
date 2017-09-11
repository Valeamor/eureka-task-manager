package com.guorenbao.taskmanager;

import com.guorenbao.generator.GeneratorUtil;


public class MybatisGenerator {
  private static String output = "";
  private static String module = "/mybatis/generator";

  public static void main(String[] args) throws Exception {
    createGeneratedBean(true);
    createGeneratedXmlAndCreteria(true);
    createCustomBeanAndXml(true);
  }

  static void createGeneratedBean(boolean override) throws Exception {
    String configXml = module + "/generatorConfig-1-gene-bean.xml";
    GeneratorUtil.doGenerate(output, configXml, override);
  }

  static void createGeneratedXmlAndCreteria(boolean override) throws Exception {
    String configXml = module + "/generatorConfig-2-gene-xmlandcreteria.xml";
    GeneratorUtil.doGenerate(output, configXml, override);
  }

  static void createCustomBeanAndXml(boolean override) throws Exception {
    String configXml = module + "/generatorConfig-3-gene-custompart.xml";
    GeneratorUtil.doGenerate(output, configXml, override);
  }
}
