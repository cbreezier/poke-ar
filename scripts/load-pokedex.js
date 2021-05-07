const fetch = require('node-fetch');

let argv = process.argv.slice(2);

if (argv.length < 1) {
    console.error(`Usage: ${process.argv[1]} <maxPokedexNum>`);
    process.exit(1);
}

const POKE_API_BASE = 'https://pokeapi.co/api/v2';

(async () => {
    let maxPokedexNum = argv[0];
    for (let pokedexNum = 1; pokedexNum <= maxPokedexNum; pokedexNum++) {
        const response = await fetch(`${POKE_API_BASE}/pokemon/${pokedexNum}`);
        const entry = await response.json();

        const id = entry['id'];
        const name = entry['name'].capitalize();

        const hp = entry['stats'][0]['base_stat'];
        const atk = entry['stats'][1]['base_stat'];
        const def = entry['stats'][2]['base_stat'];
        const satk = entry['stats'][3]['base_stat'];
        const sdef = entry['stats'][4]['base_stat'];
        const spd = entry['stats'][5]['base_stat'];

        const type1 = entry['types'][0]['type']['name'].toUpperCase();
        const type2 = entry['types'][1]?.['type']?.['name']?.toUpperCase();

        console.log(`${id} to Pokedex(${id}, "${name}", ${hp}, ${atk}, ${def}, ${satk}, ${sdef}, ${spd}, Type.${type1}, ${type2 ? `Type.${type2}` : 'null'}),`);
    }
})();

String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}
