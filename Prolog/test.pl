:- initialization(main).
main :- nQueen(6).

mydisplay([]) :- write('Empty List'),!.
mydisplay([Last]) :- write(Last), write('\n'), !.
mydisplay([Head | Tail]) :- write(Head), write(' '), mydisplay(Tail).

numlist(Min, Max, []) :-
    Min > Max.
numlist(Min, Max, [Min | Rest]) :-
    Min =< Max,
    Next is Min + 1,
    numlist(Next, Max, Rest).

setQueen(Row,Column).
queens(N,Queens) :- numlist(1, N, Queens).

canAttack(Row,Col,N,[]) :- false.
canAttack(Row,Col,N, QueensPositions) :-
    attackBrowse(Row, Col, N, 1, QueensPositions).

isSameRow(Ar,Ac,Br,Bc) :- Ar =:= Br.
isSameCol(Ar,Ac,Br,Bc) :- Ac =:= Bc.
isSameDiag(Ar,Ac,Br,Bc) :-
    Dr is Ar - Br,
    Dc is Ac - Bc,
    Dr =:= Dc.
isSameAntiDiag(Ar,Ac,Br,Bc) :-
    Dr is Ar - Br,
    Dc is Ac - Bc,
    Dr =:= -Dc.

isVisible(Ar,Ac,Br,Bc) :- isSameRow(Ar,Ac,Br,Bc).
isVisible(Ar,Ac,Br,Bc) :- isSameCol(Ar,Ac,Br,Bc).
isVisible(Ar,Ac,Br,Bc) :- isSameDiag(Ar,Ac,Br,Bc).
isVisible(Ar,Ac,Br,Bc) :- isSameAntiDiag(Ar,Ac,Br,Bc).

attackBrowse(Row,Col,N, OtherRow, []) :- false.
attackBrowse(Row,Col,N, OtherRow, [OtherCol | Queens]) :-
    \+ isVisible(Row,Col,OtherRow,OtherCol),
    Next is OtherRow+1,
    attackBrowse(Row,Col,N,Next, Queens).

nQueen(PlacedQueens,N,QueensPositions) :-
    PlacedQueens is N,
    mydisplay(QueensPositions).
nQueen(PlacedQueens,N,QueensPositions) :-
    Queen is PlacedQueens+1,
    Row is Queen,
    between(1, N, Col),
    not(canAttack(Row,Col,N,QueensPositions)),
    setQueen(Row,Col),
    append(QueensPositions,[Col],Extended),
    nQueen(Queen,N,Extended).

nQueen(N) :- nQueen(0,N,[]).