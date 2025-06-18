import { useEffect, useState } from "react";
import { Button, ButtonGroup, Container, Table } from "reactstrap";
import AppNavbar from "./AppNavbar";
import { Link } from "react-router-dom";

// Display a list of groups from the backend
const GroupList = () => {
  // 'groups' will hold the data fetched from the API
  // 'loading' indicates whether the data is still loading
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);

    // Fetch group data from backend API
    fetch("api/groups")
      .then((response) => response.json()) // Parse the JSON response
      .then((data) => {
        setGroups(data); // Store the groups in State
        setLoading(false);
      });
  }, []); // Empty dependency array means this only runs once on mount

  // Delete a group by ID
  const remove = async (id) => {
    await fetch(`/api/group/${id}`, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    }).then(() => {
      // After deletion, update the list by filtering out the deleted group
      let updatedGroups = [...groups].filter((i) => i.id !== id);
      setGroups(updatedGroups);
    });
  };

  // Show a loading message while data is being fetched
  if (loading) {
    return <p>Loading...</p>;
  }

  // Map the group data into table rows
  const groupList = groups.map((group) => {
    // Format the address string
    const address = `${group.address || ""} ${group.city || ""} ${group.stateOrProvince || ""}`;
    return (
      <tr key={group.id}>
        <td style={{ whiteSpace: "nowrap" }}>{group.name}</td>
        <td>{address}</td>
        <td>
          {/* Map through each event for the group and format the date nicely */}
          {group.events.map((event) => {
            return (
              <div key={event.id}>
                {new Intl.DateTimeFormat("en-US", {
                  year: "numeric",
                  month: "long",
                  day: "2-digit",
                }).format(new Date(event.date))}
                : {event.title}
              </div>
            );
          })}
        </td>
        <td>
          <ButtonGroup>
            {/* 'Edit' button navigates to group detail page */}
            <Button
              size="sm"
              color="primary"
              tag={Link}
              to={"/groups/" + group.id}
            >
              Edit
            </Button>
            {/* 'Delete' button calls the remove() function */}
            <Button size="sm" color="danger" onClick={() => remove(group.id)}>
              Delete
            </Button>
          </ButtonGroup>
        </td>
      </tr>
    );
  });

  // Return the full rendered component
  return (
    <div>
      <AppNavbar /> {/* Top navigation bar */}
      <Container fluid>
        <div className="float-end">
          {/* 'Add Group' button goes to the new group creation form */}
          <Button color="success" tag={Link} to="/groups/new">
            Add Group
          </Button>
        </div>
        <h3>My JUG Tour</h3>
        <Table className="mt-4">
          <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th>Events</th>
              <th width="10%">Actions</th>
            </tr>
          </thead>
          <tbody>{groupList}</tbody>
        </Table>
      </Container>
    </div>
  );
};

export default GroupList;
