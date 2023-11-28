% Movie database with genres
genre('The Shawshank Redemption', drama).
genre('The Godfather', crime).
genre('The Dark Knight', action).
genre('Pulp Fiction', crime).
genre('Forrest Gump', drama).
genre('The Matrix', action).
genre('Inception', thriller).
genre('The Silence of the Lambs', thriller).
genre('Schindler\'s List', biography).
genre('The Lord of the Rings: The Return of the King', fantasy).

% Recommendation based on genre
recommendation(Genre) :-
    write('Enter a genre to get a movie recommendation: '),
    read(InputGenre),
    nl,
    findall(Movie, (genre(Movie, InputGenre), Movie \= ''), Movies),
    random_member(Recommendation, Movies),
    write('If you like '), write(InputGenre), write(' movies, I recommend: '), write(Recommendation), nl.
