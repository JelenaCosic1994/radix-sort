# radix-sort

Radiks sort (engl. Radix sort) je algoritam sortiranja koji koristi pozicionu reprezentaciju 
i odvojeno analizira cifre (ili znakove) na različitim pozicijama. Za demonstraciju ideje algoritma, 
bez gubitka opštosti, pretpostavlja se da su ključevi decimalni brojevi sa istim brojem od k cifara dk-1 dk-2 .. d0.

Osnobna ideja je razdvajanje brojeva u 10 grupa na osnovu vrednosti prve, najstarije cifre dk-1. 
Time se izvrši grubo sortiranje jer je svaki broj iz grupe koja odgovara manjoj prvoj cifri manji od brojeva iz grupa 
koje odgovaraju većoj prvoj cifri. Zatim se izvrši sortiranje u okviru svake grupe na po 10 podgrupa u zavisnosti od 
vrednosti druge cifre dk-2. Postupak se rekurzivno nastavlja i završava u k koraka, pri čemu je poslednji korak 
sortiranje po najmlađoj cifri d0, posle čega je ulazni niz sortiran.
