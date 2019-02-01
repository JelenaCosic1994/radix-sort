# radix-sort

Radiks sort (engl. Radix sort) je algoritam sortiranja koji koristi pozicionu reprezentaciju 
i odvojeno analizira cifre (ili znakove) na različitim pozicijama. Za demonstraciju ideje algoritma, 
bez gubitka opštosti, pretpostavlja se da su ključevi decimalni brojevi sa istim brojem od k cifara dk-1 dk-2 .. d0.

Osnovna ideja je razdvajanje brojeva u 10 grupa na osnovu vrednosti prve, najstarije cifre dk-1. 
Time se izvrši grubo sortiranje jer je svaki broj iz grupe koja odgovara manjoj prvoj cifri manji od brojeva iz grupa 
koje odgovaraju većoj prvoj cifri. Zatim se izvrši sortiranje u okviru svake grupe na po 10 podgrupa u zavisnosti od 
vrednosti druge cifre dk-2. Postupak se rekurzivno nastavlja i završava u k koraka, pri čemu je poslednji korak 
sortiranje po najmlađoj cifri d0, posle čega je ulazni niz sortiran.

Suština ovog algoritma je u stabilnosti procesa sortiranja, koja je omogućena ubacivanjem elemenata u red na njegov kraj.
Prema tome, kada se u i-tom koraku vrši sortiranje po cifri di-1, veći je onaj element koji ima višu cifru na toj poziciji
i on ide u odgovarajući red. Elementi sa istom cifrom idu u isti red, ali su oni zbog stabilnosti metoda već uređeni po nižim ciframa. 
Ovo omogućava spajanje redova bez vođenja računa o njihovim granicama.
 
Vremenska složenost metoda zavisi od broja cifara k i od broja elemenata n. 
Kako se u svakom od k koraka obrađuju svi elementi, vremenska složenost je reda O(kn). 
