const app = Vue.createApp({
    setup() {
    const pokemonA= Vue.ref('');
    const pokemonB=Vue.ref('');

        let pokemonName = Vue.ref('');
        const Points = Vue.ref(0);
        function battle() {
        let url = `http://localhost:8081/attack?pokemonA=${pokemonA.value}&pokemonB=${pokemonB.value}`;
        console.log("======= : " + url);
            fetch(url)
                .then(response => response.json())
                .then(data => {
                console.log("data : " + data);
                    pokemonName.value = data.winner;
                    Points.value=data.hitPoints;
                    console.log(`This pokemon has ${data.hitPoints} hit points.`);
                }).catch(e=>{
                console.error('error occured in battle ',e)})
        }
        return {
            pokemonName,
            pokemonA,
            pokemonB,
            battle,
            Points,
        }
    }
});
app.mount('#app');
