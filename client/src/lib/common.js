import { get } from 'svelte/store';
import { token, name, fullName } from '$lib/store.js';

export async function getUser() {
  const response = await fetch('/api/user', {
    method: 'get',
    headers: {
      'Authorization': 'Bearer ' + get(token),
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    withCredentials: true,
  });
  if (response.ok) {
      const j = await response.json();
      name.set(j.name);
      fullName.set(j.fullName);
  } else {
    // console.log("Local storage cleared ....");
    localStorage.clear()
  }
}
