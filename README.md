## 1 -  Spring  boot  task  blocker  
  
**Aplicação de controle de tarefas agendadas** 📅  
  
Vamos supor que você queira que sua aplicação execute um processamento, ou tarefa, a cada 10 minutos, ou no período que você preferir, esse é o projeto que você procura!!!  
  
Shedlock (https://github.com/lukas-krecan/ShedLock) é uma biblioteca Java que garante que nossas tarefas agendadas, um simples método(), sejam executadas apenas uma única vez por tempo. Ele garante que mesmo com várias instâncias com a nossa aplicação sendo executadas ao mesmo tempo, a nossa tarefa será executada apenas 1 vez por tempo.  
  
Vamos supor que desejamos fazer uma busca em uma base de dados e processar essas consultas. Não precisaríamos bloquear a tarefas durante todo o período de busca até a finalização do processamento, o bloqueio só seria necessário durante a busca desses dados, após isso, o processamento é indiferente!  
  
O Shedlock bloqueia a tarefa pelo período que nós definimos e caso o processamento dessa tarefa termine antes desse período ele a desbloqueia automaticamente.  
  
**Problemas:**  
- Não é possível paralelizar a execução de uma única tarefa entre as **N** instâncias ❎  
- Controle totalmente automático de bloqueio e desbloqueio das tarefas 🤖  
  
![Execução do Shedlock](https://raw.githubusercontent.com/MarquesCleiton/brincando-com-java/main/spring-boot-task-blocker/imgs/1-shedlock.png)  
  
**Solução:**  
+ Criar nosso prórpio  "Shedlock", utilizando: JPA e AOP  
+ JPA: para controle e persistência no banco de dados  
+ AOP: Programaçãoorientada a aspecto, para implementar funções de bloqueio e desbloqueio antes e depois da execução de cada uma de nossas tarefas.  
  
![Paralelismo](https://raw.githubusercontent.com/MarquesCleiton/brincando-com-java/main/spring-boot-task-blocker/imgs/2-paralelismo.png)  

