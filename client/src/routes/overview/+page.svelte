<script>
  import { onMount } from 'svelte'
  import { goto } from '$app/navigation';
  import { token, name, fullName } from "$lib/store.js"
  import { getUser } from '$lib/common.js'

  onMount(async () => {
    let t = localStorage.getItem('token');
    if (t != null) {
      token.set(t);
      await getUser();
    }
  });
</script>

<article class='prose'>
  <h1>Overview</h1>

  <h2>Username  : {$name}</h2>
  <h2>Full Name : {$fullName}</h2>
</article>
