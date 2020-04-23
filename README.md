# sri.mq
sri projekt 3
Aplikacja co 15 sekund generuje informacje na temat bolidu poprzez klase /resource/DataDistributor i wysyla topic odbierany przez /listener/Logger oraz /router/CalmelRouter. Logger zapisuje aktualny stan bolidu do pliku mq_logs.log
Router przekierowuje wiadomosci albo bezposrednio do kolejki kierowcy albo kierowcy i drivera. Wiadomosci odbieraja we wlasnym zakresie w klasach /listener/Driver oraz /listener/Mechanics. Jesli zaden z parametrow nie zostal
przekroczony, wowczas nigdzie wiadomosci nie sa przekierowywane. Aby uruchomic rzadanie zjechania do pitstopu nalezy w przegladarce uruchomic wyzwalacz wchodzac w link:  http://localhost:8081/RequestPitstop
PitStop moze zgodzic sie lub odrzucic prosbe. Wszystkie kroki wypisywane sa na ekran poleceniem sout. Ze znanych bledow: router nie przekierowuje pierwszej wiadomosci, wszystkie kolejne juz tak.
