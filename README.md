# Jad Backend

## Instructiuni pentru pull request

### Initializare repo
1. Deschide un terminal in folderul unde vei salva cele doua repo-uri
2. Executa `git clone https://github.com/jad-tracker/jad-backend.git`

### Rezolvarea unui task
1. Pe GitHub creeaza un nou branch remote cu nume sugestiv pt task-ul pe care il rezolvi
2. Deschide un terminal din IntelliJ (verifica calea curenta sa fie folderul proiectului de backend)
3. Verifica ca esti pe branch-ul `main` (daca nu, executa `git checkout main`)
4. Executa `git pull`
5. Executa `git remote update --prune origin`
6. Executa `git checkout nume_branch_remote`, unde `nume_branch_remote` e numele folosit la pasul 1
7. Acum poti scrie cod pentru a rezolva task-ul
8. Neaparat testele relevante pentru task trebuie activate, iar toate testele active trebuie sa treaca cu succes
9. Adauga modificarile din IDE (sau cu `git add`) si dupa fa commit din IDE (sau cu `git commit -m "mesaj de commit"`). 
Foloseste un mesaj de commit relevant
10. Executa `git push -u origin nume_branch_remote`, unde `nume_branch_remote` e numele folosit la pasul 1
11. Creeaza un pull request pe github din branch-ul nou pe main. Refera numarul issue-ului in corpul PR-ului sau in titlu.
12. Cere code review

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

## Alte aspecte importante

### Activare teste
La sectiunea `Rezolvarea unui task` la pasul 6. se vorbeste despre teste relevante pentru un task. In aceasta sectiune se clarifica conceptul.

1. Testele relevante pentru un task sunt cele care sunt marcate cu `@Tag("TAGJIRA")`, unde `TAGJIRA` este tag-ul din Jira
pentru task-ul tau (exemple: JAD-3, JAD-5, JAD-7)
2. Toate testele care au tagul respectiv trebuie activate prin eliminarea `@Disabled` din fata lor
3. Restul testelor NU trebuie atinse (cele activate deja raman activate)
4. Se ruleaza toate testele si se observa cum cele noi pica
5. Pe baza descrierii task-ului din Jira si pe baza codului din test se deduce ce fel de cod trebuie scris
6. Dupa ce se rezolva task-ul, toate testele trebuie sa treaca

### Modul de lucru al arhitecturii
Request -> Dto -> Controller -> Service -> Repo -> Entitati/Model ->
Entitati/Model -> Repo -> Service -> Controller -> Dto -> Response

- Controllerul are cate o metoda pentru fiecare endpoint/ruta (exemplu: `GET /api/projects` -> `getProjects`)
- Service-ul ofera metode pentru a implementa operatiile necesare intr-un endpoint (de obicei o metoda din service corespunde unei metode din controller).
Metodele astea primesc dto-uri de la controller si returneaza dto-uri
- Service-ul foloseste mai multe metode din diverse repo-uri (minim Repo-ul entitatii asociate cu service-ul si controller-ul)
- Conversia dintre dto-uri si entitati in Service se face prin DtoMapper (ofera metode pt conversia din entitate in dto, lista de entitati in lista de dto-uri, respectiv din dto in entitate)
- La conversia din dto in entitate se mai aplica niste validari ca datele sa fie corecte (altfel se arunca exceptii custom - `InvalidEntityException`, `EntityNotFoundException`)
- Dto-urile se creeaza pe baza structurii body-ului din request si response si se numesc corespunzator
- Sfat: urmati modul cum sunt implementate rutele la Proiecte/Useri
