:- initialization(main).

% ============================================================
% Code initial fourni
% ============================================================
setQueen(_, _).
queens(N, Queens) :- findall(X, between_col(1, N, X), Queens).

between_col(Low, High, Low) :- Low =< High.
between_col(Low, High, X)   :- Low < High, Low1 is Low + 1, between_col(Low1, High, X).

printList([])    :- nl, !.
printList([H|T]) :- write(H), write(' '), printList(T).

% ============================================================
% All your rules here ...
% ============================================================

% Vrai si deux reines se menacent
canAttack(Col1, Col2, _) :-
    Col1 =:= Col2.
canAttack(Col1, Col2, RowDiff) :-
    Diff is abs(Col1 - Col2),
    Diff =:= RowDiff.

% Vérifie que la reine en Col ne conflicte avec aucune reine placée
canPlaceQueen(_, [], _) :- !.
canPlaceQueen(Col, [H|T], RowDiff) :-
    \+ canAttack(Col, H, RowDiff),
    Next is RowDiff + 1,
    canPlaceQueen(Col, T, Next).

% ============================================================
% nQueen/3 à compléter
% ============================================================

% Cas terminal : toutes les reines sont placées
nQueen(N, N, QueensPositions) :-
    write('Solution: '),
    printList(QueensPositions), !.

% Cas général
nQueen(PlacedQueens, N, QueensPositions) :-
    PlacedQueens < N,
    queens(N, Cols),
    member(Col, Cols),
    canPlaceQueen(Col, QueensPositions, 1),
    setQueen(PlacedQueens, Col),
    Next is PlacedQueens + 1,
    nQueen(Next, N, [Col|QueensPositions]).

% ============================================================
% Point d'entrée
% ============================================================
nQueen(N) :- nQueen(0, N, []).

main :- nQueen(6).