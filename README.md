# Jad Backend

## Instructiuni pentru pull request

### Initializare repo
1. Deschide un terminal in folderul unde vei salva cele doua repo-uri
2. Executa `git clone https://github.com/jad-tracker/jad-backend.git`

### Rezolvarea unui task
1. Pe GitHub creeaza un nou branch remote cu nume sugestiv pt task-ul pe care il rezolvi
2. Deschide un terminal din IntelliJ (verifica calea curenta sa fie folderul proiectului de backend)
3. Executa `git remote update --prune origin`
4. Executa `git checkout nume_branch_remote`, unde `nume_branch_remote` e numele folosit la pasul 1
5. Acum poti scrie cod pentru a rezolva task-ul
6. Neaparat testele relevante pentru task trebuie activate, iar toate testele active trebuie sa treaca cu succes
7. Adauga modificarile din IDE (sau cu `git add`) si dupa fa commit din IDE (sau cu `git commit -m "mesaj de commit"`). 
Foloseste un mesaj de commit relevant
8. Executa `git push -u origin nume_branch_remote`, unde `nume_branch_remote` e numele folosit la pasul 1
9. Creeaza un pull request pe github din branch-ul nou pe main. Refera numarul issue-ului in corpul PR-ului sau in titlu.
10. Cere code review

## Instructiuni de rulare

### Rulare DB

1. Instaleaza Docker
2. Ruleaza `Docker Desktop`
3. Deschide terminalul din IntelliJ (verifica calea curenta sa fie folderul proiectului de backend)
4. Executa `docker compose up -d --build`
5. Postgres o sa ruleze pe `localhost:7071`

### Oprire DB
1. Deschide terminalul din IntelliJ (verifica calea curenta sa fie folderul proiectului de backend)
2. Executa `docker compose down`
3. Containerul de DB se va opri

### Rulare proiect

IMPORTANT: DB-ul trebuie deja sa ruleze inainte de pasul asta

Ruleaza din IntelliJ `JadBackendApplication`

### Rulare teste

IMPORTANT: Testele folosesc un DB in memorie, nu necesita sa ruleze DB-ul de sus

Ruleaza din IntelliJ testele din pachetul `ro.ubbcluj.tpjad.jadbackend`:
- Run configuration-ul `All tests in jad-backend`
- Daca run configuration-ul nu e disponibil: click dreapta pe pachet, optiunea `Run 'Tests in 'ro.ubbcluj.tpjad.jadbackend''`  